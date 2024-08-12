package br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.CheckAccessMain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MenuInicialState(
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
)

class MenuInicialViewModel(
    private val checkAccessMain: CheckAccessMain
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuInicialState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun checkAccess() =
        viewModelScope.launch {
            val resultCheckAccess = checkAccessMain()
            if(resultCheckAccess.isFailure){
                val error = resultCheckAccess.exceptionOrNull()!!
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
            val statusAccess = resultCheckAccess.getOrNull()!!
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