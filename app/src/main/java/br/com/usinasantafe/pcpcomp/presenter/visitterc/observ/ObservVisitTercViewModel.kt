package br.com.usinasantafe.pcpcomp.presenter.visitterc.observ

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ObservVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val typeMov: TypeMov = TypeMov.INPUT,
    val observ: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ObservVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ObservVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onObservChanged(observ: String) {
        _uiState.update {
            it.copy(observ = observ)
        }
    }
    fun setObserv() {
        var checkObservNotEmpty = true
        if (uiState.value.observ.isEmpty()) {
            checkObservNotEmpty = false
        }
        viewModelScope.launch {
        }
    }

}