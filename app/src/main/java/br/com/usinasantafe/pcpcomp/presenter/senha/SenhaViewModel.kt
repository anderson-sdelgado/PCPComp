package br.com.usinasantafe.pcpcomp.presenter.senha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.CheckPasswordConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SenhaState(
    val password: String = "",
    val statusDialog: Boolean = false,
    val statusAccess: Boolean = false
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
            it.copy(statusDialog = false)
        }
    }

    fun checkPassword() =
        viewModelScope.launch {
            val statusAccess = checkPasswordConfig(password = uiState.value.password)
            val statusDialog = !statusAccess
            _uiState.update {
                it.copy(
                    statusDialog = statusDialog,
                    statusAccess = statusAccess
                )
            }
        }

}