package br.com.usinasantafe.pcpcomp.presenter.visitterc.placa

import androidx.lifecycle.ViewModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PlacaVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val placa: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class PlacaVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlacaVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onPlacaChanged(placa: String) {
        _uiState.update {
            it.copy(placa = placa)
        }
    }

    fun setPlaca() {

    }

}