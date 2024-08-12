package br.com.usinasantafe.pcpcomp.presenter.configuration.senha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.config.CheckPasswordConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SenhaState(
    val password: String = "",
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
)

class SenhaViewModel(
    private val checkPasswordConfig: CheckPasswordConfig
) : ViewModel() {

    private val _uiState = MutableStateFlow(SenhaState())
    val uiState = _uiState.asStateFlow()

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun checkPassword() =
        viewModelScope.launch {
            val resultCheckPassword = checkPasswordConfig(password = uiState.value.password)
            if(resultCheckPassword.isFailure) {
                val error = resultCheckPassword.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagAccess = false,
                        flagFailure = true,
                        failure = failure
                    )
                }
                return@launch
            }
            val statusAccess = resultCheckPassword.getOrNull()!!
            val statusDialog = !statusAccess
            _uiState.update {
                it.copy(
                    flagDialog = statusDialog,
                    flagAccess = statusAccess,
                    flagFailure = false,
                )
            }
        }

}