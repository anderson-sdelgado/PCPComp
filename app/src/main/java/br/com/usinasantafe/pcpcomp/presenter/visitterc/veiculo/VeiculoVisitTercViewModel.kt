package br.com.usinasantafe.pcpcomp.presenter.visitterc.veiculo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VeiculoVisitTercState(
    val veiculo: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class VeiculoVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(VeiculoVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onVeiculoChanged(veiculo: String) {
        _uiState.update {
            it.copy(veiculo = veiculo)
        }
    }

    fun setVeiculo() {
        viewModelScope.launch {

        }
    }

}