package br.com.usinasantafe.pcpcomp.presenter.menuinicial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MenuInicialState(
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
)

class MenuInicialViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(MenuInicialState())
    val uiState = _uiState.asStateFlow()

    fun checkAccess() =
        viewModelScope.launch {
        }
}