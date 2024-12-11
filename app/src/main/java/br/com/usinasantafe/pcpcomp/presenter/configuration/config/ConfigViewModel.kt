package br.com.usinasantafe.pcpcomp.presenter.configuration.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocalTrab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateVisitante
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.percentage
import br.com.usinasantafe.pcpcomp.utils.sizeUpdate
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

fun ResultUpdate.resultUpdateToConfig(): ConfigState {
    return with(this){
        ConfigState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = this.failure,
            flagProgress = this.flagProgress,
            msgProgress = this.msgProgress,
            currentProgress = this.currentProgress,
        )
    }
}

class ConfigViewModel(
    private val getConfigInternal: GetConfigInternal,
    private val sendDataConfig: SendDataConfig,
    private val saveDataConfig: SaveDataConfig,
    private val updateChave: UpdateChave,
    private val updateColab: UpdateColab,
    private val updateEquip: UpdateEquip,
    private val updateFluxo: UpdateFluxo,
    private val updateLocal: UpdateLocal,
    private val updateLocalTrab: UpdateLocalTrab,
    private val updateRLocalFluxo: UpdateRLocalFluxo,
    private val updateTerceiro: UpdateTerceiro,
    private val updateVisitante: UpdateVisitante,
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
                    number = configModel.number,
                    password = configModel.password
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
                currentProgress = percentage(1f, sizeToken)
            )
        )
        val resultSend = sendDataConfig(
            number = number,
            password = password,
            version = version
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
                currentProgress = percentage(2f, sizeToken),
            )
        )
        val resultSave = saveDataConfig(
            number = number,
            password = password,
            version = version,
            idBD = resultSend.getOrNull()!!
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
        var pos = 0f
        val sizeUpdate = sizeUpdate(9f)
        var configState = ConfigState()
        updateChave(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateColab(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateEquip(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateFluxo(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateLocal(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateLocalTrab(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateRLocalFluxo(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateTerceiro(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
        updateVisitante(sizeUpdate, ++pos).collect{
            configState = it.resultUpdateToConfig()
            emit(it.resultUpdateToConfig())
        }
        if(configState.flagFailure) return@flow
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

}