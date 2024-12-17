package br.com.usinasantafe.pcpcomp.presenter.chave.matriccolab

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetMatricColabMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateColab
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabState
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.resultUpdateToMatricColab
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatricColabChaveState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val matricColab: String = "",
    val checkGetMatricColab: Boolean = true,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToMatricColabChave(): MatricColabChaveState {
    return with(this){
        MatricColabChaveState(
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

class MatricColabChaveViewModel(
    saveStateHandle: SavedStateHandle,
    private val updateColab: UpdateColab,
    private val checkMatricColab: CheckMatricColab,
    private val getMatricColabMovChave: GetMatricColabMovChave
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(MatricColabChaveState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun getMatricColab() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetMatricColab)
        ) {
            val resultGetMatric = getMatricColabMovChave(uiState.value.id)
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

    suspend fun updateAllDatabase(): Flow<MatricColabChaveState> = flow {
        val sizeUpdate = 4f
        var state = MatricColabChaveState()
        updateColab(sizeUpdate, 1f).collect {
            state = it.resultUpdateToMatricColabChave()
            emit(it.resultUpdateToMatricColabChave())
        }
        if (state.flagFailure)
            return@flow
        emit(
            MatricColabChaveState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}