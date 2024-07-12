package br.com.usinasantafe.pcpcomp.presenter.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.RecoverConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConfigState(
    val number: String = "",
    val password: String = "",
    val statusDialog: Boolean = false
)

class ConfigViewModel(
    private val recoverConfig: RecoverConfig,
) : ViewModel()  {

    private val _uiState = MutableStateFlow(ConfigState())
    val uiState = _uiState.asStateFlow()

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun updateNumber(number: String) {
        _uiState.update {
            it.copy(number = number)
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(statusDialog = false)
        }
    }

    fun returnDataConfig() =
        viewModelScope.launch {
            recoverConfig()?.let { configModel ->
                _uiState.update {
                    it.copy(
                        number = configModel.number,
                        password = configModel.password
                    )
                }
            }
        }

    fun saveDataAndUpdateAllDatabase() {
        if (uiState.value.number.isEmpty() || uiState.value.password.isEmpty()) {
            _uiState.update {
                it.copy(statusDialog = true)
            }
            return
        }
    }

}