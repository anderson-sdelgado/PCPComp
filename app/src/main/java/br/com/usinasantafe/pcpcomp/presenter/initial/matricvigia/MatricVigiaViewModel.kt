package br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateColab
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatricVigiaState(
    val matricVigia: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToMatricVigia(): MatricVigiaState {
    return with(this){
        MatricVigiaState(
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

class MatricVigiaViewModel(
    private val checkMatricColab: CheckMatricColab,
    private val setMatricVigiaConfig: SetMatricVigiaConfig,
    private val updateColab: UpdateColab,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatricVigiaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(
                flagDialog = false
            )
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ){
        when(typeButton){
            TypeButton.NUMERIC -> {
                val matricVigia = addTextField(uiState.value.matricVigia, text)
                _uiState.update {
                    it.copy(matricVigia = matricVigia)
                }
            }
            TypeButton.CLEAN -> {
                val matricVigia = clearTextField(uiState.value.matricVigia)
                _uiState.update {
                    it.copy(matricVigia = matricVigia)
                }
            }
            TypeButton.OK -> {
                if (uiState.value.matricVigia.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                setMatricVigia()
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

    private fun setMatricVigia() = viewModelScope.launch {
        val resultCheckMatric = checkMatricColab(uiState.value.matricVigia)
        if(resultCheckMatric.isFailure){
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
        val resultSetMatric = setMatricVigiaConfig(uiState.value.matricVigia)
        if(resultSetMatric.isFailure){
            val error = resultSetMatric.exceptionOrNull()!!
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
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
                flagFailure = !result,
                errors = Errors.INVALID,
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<MatricVigiaState> = flow {
        val sizeUpdate = 4f
        var configState = MatricVigiaState()
        updateColab(sizeUpdate, 1f).collect{
            configState = it.resultUpdateToMatricVigia()
            emit(it.resultUpdateToMatricVigia())
        }
        if(configState.flagFailure)
            return@flow
        emit(
            MatricVigiaState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}