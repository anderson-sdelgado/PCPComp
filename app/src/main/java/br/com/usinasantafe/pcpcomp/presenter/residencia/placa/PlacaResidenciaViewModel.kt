package br.com.usinasantafe.pcpcomp.presenter.residencia.placa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlacaResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val placa: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class PlacaResidenciaViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlacaResidenciaState())
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
        if (uiState.value.placa.isEmpty()) {
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