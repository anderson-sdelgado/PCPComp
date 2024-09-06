package br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VeiculoResidenciaState (
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val veiculo: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class VeiculoResidenciaViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(VeiculoResidenciaState())
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
        if (uiState.value.veiculo.isEmpty()) {
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