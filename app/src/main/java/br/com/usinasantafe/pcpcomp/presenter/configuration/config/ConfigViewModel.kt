package br.com.usinasantafe.pcpcomp.presenter.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllEquipServer
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllLocalServer
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllTerceiroServer
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllVisitanteServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.TB_COLAB
import br.com.usinasantafe.pcpcomp.utils.TB_EQUIP
import br.com.usinasantafe.pcpcomp.utils.TB_LOCAL
import br.com.usinasantafe.pcpcomp.utils.TB_TERCEIRO
import br.com.usinasantafe.pcpcomp.utils.TB_VISITANTE
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConfigState(
    val number: String = "",
    val password: String = "",
    val version: String = "",
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELDEMPTY,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

class ConfigViewModel(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val cleanColab: CleanColab,
    private val cleanEquip: CleanEquip,
    private val cleanLocal: CleanLocal,
    private val cleanTerceiro: CleanTerceiro,
    private val cleanVisitante: CleanVisitante,
    private val getAllColabServer: GetAllColabServer,
    private val getAllEquipServer: GetAllEquipServer,
    private val getAllLocalServer: GetAllLocalServer,
    private val getAllTerceiroServer: GetAllTerceiroServer,
    private val getAllVisitanteServer: GetAllVisitanteServer,
    private val saveAllColab: SaveAllColab,
    private val saveAllEquip: SaveAllEquip,
    private val saveAllLocal: SaveAllLocal,
    private val saveAllTerceiro: SaveAllTerceiro,
    private val saveAllVisitante: SaveAllVisitante,
    private val setCheckUpdateAllTable: SetCheckUpdateAllTable,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun updateVersion(version: String) {
        _uiState.update {
            it.copy(version = version)
        }
    }

    fun onNumberChanged(number: String) {
        _uiState.update {
            it.copy(number = number)
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnDataConfig() = viewModelScope.launch {
        val recoverConfig = getConfigInternal()
        if (recoverConfig.isFailure) {
            val error = recoverConfig.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    errors = Errors.EXCEPTION,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = recoverConfig.getOrNull()
        result?.let { configModel ->
            _uiState.update {
                it.copy(
                    number = configModel.number, password = configModel.password
                )
            }
        }
    }

    fun saveTokenAndUpdateAllDatabase() {
        if (uiState.value.number.isEmpty() || uiState.value.password.isEmpty()) {
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.FIELDEMPTY,
                    flagFailure = true,
                )
            }
            return
        }
        viewModelScope.launch {
            token().collect { configStateToken ->
                _uiState.value = configStateToken
                if((!configStateToken.flagFailure) && (configStateToken.currentProgress == 1f)){
                    updateAllDatabase().collect { configStateUpdate ->
                        _uiState.value = configStateUpdate
                    }
                }
            }
        }
    }

    suspend fun token(): Flow<ConfigState> = flow {
        val sizeToken = 3f
        val number = uiState.value.number
        val password = uiState.value.password
        val version = uiState.value.version
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = porc(1f, sizeToken)
            )
        )
        val resultSend = sendDataConfig(
            number = number, password = password, version = version
        )
        if (resultSend.isFailure) {
            val error = resultSend.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = porc(2f, sizeToken),
            )
        )
        val resultSave = saveDataConfig(
            number = number, password = password, version = version, idBD = resultSend.getOrNull()!!
        )
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.TOKEN,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Ajuste iniciais finalizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllDatabase(): Flow<ConfigState> = flow {
        val sizeUpdate = 16f
        var configState = ConfigState()
        updateAllColab(sizeUpdate, 1f).collect{ configStateColab ->
            configState = configStateColab
            emit(configStateColab)
        }
        if(configState.flagFailure)
            return@flow
        updateAllEquip(sizeUpdate, 2f).collect{ configStateEquip ->
            configState = configStateEquip
            emit(configStateEquip)
        }
        if(configState.flagFailure)
            return@flow
        updateAllLocal(sizeUpdate, 3f).collect{ configStateLocal ->
            configState = configStateLocal
            emit(configStateLocal)
        }
        if(configState.flagFailure)
            return@flow
        updateAllTerceiro(sizeUpdate, 4f).collect{ configStateTerceiro ->
            configState = configStateTerceiro
            emit(configStateTerceiro)
        }
        if(configState.flagFailure)
            return@flow
        updateAllVisitante(sizeUpdate, 5f).collect{ configStateVisitante ->
            configState = configStateVisitante
            emit(configStateVisitante)
        }
        if(configState.flagFailure)
            return@flow
        val result = setCheckUpdateAllTable(FlagUpdate.UPDATED)
        if(result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.EXCEPTION,
                    flagFailure = true,
                    flagDialog = true,
                    failure = failure,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagDialog = true,
                flagProgress = true,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllColab(sizeAll: Float, count: Float): Flow<ConfigState> = flow {
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela ${TB_COLAB}",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanColab()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela ${TB_COLAB} do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllColabServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela ${TB_COLAB}",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllColab(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

    suspend fun updateAllEquip(sizeAll: Float, count: Float): Flow<ConfigState> = flow {
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela ${TB_EQUIP}",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanEquip()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela ${TB_EQUIP} do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllEquipServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela ${TB_EQUIP}",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllEquip(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

    suspend fun updateAllLocal(sizeAll: Float, count: Float): Flow<ConfigState> = flow {
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela ${TB_LOCAL}",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanLocal()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela ${TB_LOCAL} do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllLocalServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela ${TB_LOCAL}",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllLocal(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }


    suspend fun updateAllTerceiro(sizeAll: Float, count: Float): Flow<ConfigState> = flow {
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela ${TB_TERCEIRO}",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanTerceiro()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela ${TB_TERCEIRO} do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllTerceiroServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela ${TB_TERCEIRO}",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllTerceiro(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }


    suspend fun updateAllVisitante(sizeAll: Float, count: Float): Flow<ConfigState> = flow {
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela ${TB_VISITANTE}",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanVisitante()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela ${TB_VISITANTE} do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllVisitanteServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela ${TB_VISITANTE}",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllVisitante(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }
}