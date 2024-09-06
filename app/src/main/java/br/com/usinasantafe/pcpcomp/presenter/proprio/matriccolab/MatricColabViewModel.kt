package br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TB_COLAB
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatricColabState(
    val flowApp: FlowApp = FlowApp.ADD,
    val matricColab: String = "",
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val checkGetMatricColab: Boolean = true,
    val id: Int = 0,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

class MatricColabViewModel(
    saveStateHandle: SavedStateHandle,
    private val checkMatricColab: CheckMatricColab,
    private val cleanColab: CleanColab,
    private val getAllColabServer: GetAllColabServer,
    private val saveAllColab: SaveAllColab,
    private val getMatricColab: GetMatricColab,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(MatricColabState())
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
                val matricColab = addTextField(uiState.value.matricColab, text)
                _uiState.update {
                    it.copy(matricColab = matricColab)
                }
            }

            TypeButton.CLEAN -> {
                val matricColab = clearTextField(uiState.value.matricColab)
                _uiState.update {
                    it.copy(matricColab = matricColab)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.matricColab.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                setMatricColab()
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

    fun getMatricColab() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.typeOcupante == TypeOcupante.MOTORISTA) &&
            (uiState.value.checkGetMatricColab)
        ) {
            val resultGetMatric = getMatricColab(uiState.value.id)
            if (resultGetMatric.isFailure) {
                val error = resultGetMatric.exceptionOrNull()!!
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
            val matricColab = resultGetMatric.getOrNull()!!
            _uiState.update {
                it.copy(
                    matricColab = matricColab,
                    checkGetMatricColab = false
                )
            }
        }
    }

    private fun setMatricColab() = viewModelScope.launch {
        val resultCheckMatric = checkMatricColab(uiState.value.matricColab)
        if (resultCheckMatric.isFailure) {
            val error = resultCheckMatric.exceptionOrNull()!!
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
        val result = resultCheckMatric.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
                flagFailure = !result,
                errors = Errors.INVALID,
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<MatricColabState> = flow {
        val sizeUpdate = 4f
        var configState = MatricColabState()
        updateAllColab(sizeUpdate, 1f).collect { stateUpdateColab ->
            configState = stateUpdateColab
            emit(stateUpdateColab)
        }
        if (configState.flagFailure)
            return@flow
        emit(
            MatricColabState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllColab(sizeAll: Float, count: Float): Flow<MatricColabState> = flow {
        emit(
            MatricColabState(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_COLAB",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanColab()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                MatricColabState(
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
            MatricColabState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_COLAB do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = getAllColabServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                MatricColabState(
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
            MatricColabState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_COLAB",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllColab(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                MatricColabState(
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