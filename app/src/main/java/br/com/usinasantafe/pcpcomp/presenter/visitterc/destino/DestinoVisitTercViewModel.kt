package br.com.usinasantafe.pcpcomp.presenter.visitterc.destino

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DestinoVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val destino: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class DestinoVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DestinoVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onDestinoChanged(destino: String) {
        _uiState.update {
            it.copy(destino = destino)
        }
    }
    fun setDestino() {
        if (uiState.value.destino.isEmpty()) {
            _uiState.update {
                it.copy(
                    flagDialog = true,
                )
            }
            return
        }
        viewModelScope.launch {}
    }


}