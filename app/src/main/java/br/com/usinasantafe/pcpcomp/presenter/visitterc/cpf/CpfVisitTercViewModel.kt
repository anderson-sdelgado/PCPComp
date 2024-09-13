package br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllTerceiroServer
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllVisitanteServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CheckCpfVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetCpfVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetTitleCpfVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TB_TERCEIRO
import br.com.usinasantafe.pcpcomp.utils.TB_VISITANTE
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CpfVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val title: String = "",
    val cpf: String = "",
    val checkGetCpf: Boolean = true,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

class CpfVisitTercViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTitleCpfVisitTerc: GetTitleCpfVisitTerc,
    private val checkCpfVisitTerc: CheckCpfVisitTerc,
    private val getCpfVisitTerc: GetCpfVisitTerc,
    private val cleanTerceiro: CleanTerceiro,
    private val cleanVisitante: CleanVisitante,
    private val getAllTerceiroServer: GetAllTerceiroServer,
    private val getAllVisitanteServer: GetAllVisitanteServer,
    private val saveAllTerceiro: SaveAllTerceiro,
    private val saveAllVisitante: SaveAllVisitante,
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = savedStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(CpfVisitTercState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeOcupante = TypeOcupante.entries[typeOcupante],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                if (uiState.value.cpf.length < 15) {
                    var cpf = addTextField(uiState.value.cpf, text)
                    cpf = if (cpf.length == 3) "$cpf." else cpf
                    cpf = if (cpf.length == 7) "$cpf." else cpf
                    cpf = if (cpf.length == 11) "$cpf-" else cpf
                    _uiState.update {
                        it.copy(cpf = cpf)
                    }
                }
            }

            TypeButton.CLEAN -> {
                val nroEquip = clearTextField(uiState.value.cpf)
                _uiState.update {
                    it.copy(cpf = nroEquip)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.cpf.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                checkCpf()
            }

            TypeButton.UPDATE -> {
                viewModelScope.launch {
                    updateAllDatabase().collect { stateUpdate ->
                        _uiState.value = stateUpdate
                    }
                }
            }
        }
    }

    fun recoverTitle() = viewModelScope.launch {
        if (uiState.value.title.isEmpty()) {
            val resultGetTitle = getTitleCpfVisitTerc(
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
            if (resultGetTitle.isFailure) {
                val error = resultGetTitle.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                    )
                }
                return@launch
            }
            val title = resultGetTitle.getOrNull()!!
            _uiState.update {
                it.copy(title = title)
            }
        }
    }

    fun getCpf() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.typeOcupante == TypeOcupante.MOTORISTA) &&
            (uiState.value.checkGetCpf)
        ) {
            val resultGetCpf = getCpfVisitTerc(uiState.value.id)
            if (resultGetCpf.isFailure) {
                val error = resultGetCpf.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                    )
                }
                return@launch
            }
            val cpf = resultGetCpf.getOrNull()!!
            _uiState.update {
                it.copy(
                    cpf = cpf,
                    checkGetCpf = false
                )
            }
        }
    }

    private fun checkCpf() = viewModelScope.launch {
        val resultCheckCpf = checkCpfVisitTerc(
            cpf = uiState.value.cpf,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if (resultCheckCpf.isFailure) {
            val error = resultCheckCpf.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = resultCheckCpf.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
                flagFailure = !result,
                errors = Errors.INVALID,
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<CpfVisitTercState> = flow {
        val sizeUpdate = 7f
        var cpfVisitTercState = CpfVisitTercState()
        updateAllTerceiro(sizeUpdate, 1f).collect { stateUpdate ->
            cpfVisitTercState = stateUpdate
            emit(stateUpdate)
        }
        if (cpfVisitTercState.flagFailure)
            return@flow
        updateAllVisitante(sizeUpdate, 2f).collect { stateUpdate ->
            cpfVisitTercState = stateUpdate
            emit(stateUpdate)
        }
        if (cpfVisitTercState.flagFailure)
            return@flow
        emit(
            CpfVisitTercState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllTerceiro(sizeAll: Float, count: Float): Flow<CpfVisitTercState> = flow {
        emit(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_TERCEIRO",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanTerceiro()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                CpfVisitTercState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_TERCEIRO do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllTerceiroServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                CpfVisitTercState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_TERCEIRO",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllTerceiro(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                CpfVisitTercState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

    suspend fun updateAllVisitante(sizeAll: Float, count: Float): Flow<CpfVisitTercState> = flow {
        emit(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_VISITANTE",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanVisitante()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                CpfVisitTercState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_VISITANTE do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllVisitanteServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                CpfVisitTercState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_VISITANTE",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllVisitante(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                CpfVisitTercState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }
}