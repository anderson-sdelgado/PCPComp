package br.com.usinasantafe.pcpcomp.presenter.matricvigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatricVigiaState(
    val matricVigia: String = "",
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
)

class MatricVigiaViewModel(
    private val checkMatricColab: CheckMatricColab,
    private val setMatricVigiaConfig: SetMatricVigiaConfig
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatricVigiaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
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
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                viewModelScope.launch {
                    val resultCheckMatric = checkMatricColab(uiState.value.matricVigia)
                    if(resultCheckMatric.isFailure){
                        val error = resultCheckMatric.exceptionOrNull()!!
                        val failure =
                            "Error CheckMatricColab -> ${error.message} -> ${error.cause.toString()}"
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
                    val result = resultCheckMatric.getOrNull()!!
                    val resultSetMatric = setMatricVigiaConfig(uiState.value.matricVigia)
                    if(resultSetMatric.isFailure){
                        val error = resultSetMatric.exceptionOrNull()!!
                        val failure =
                            "Error SetMatricVigiaConfig -> ${error.message} -> ${error.cause.toString()}"
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
                    _uiState.update {
                        it.copy(
                            flagAccess = result,
                            flagDialog = !result,
                            flagFailure = false
                        )
                    }
                }
            }
            TypeButton.UPDATE -> {}
        }
    }

}