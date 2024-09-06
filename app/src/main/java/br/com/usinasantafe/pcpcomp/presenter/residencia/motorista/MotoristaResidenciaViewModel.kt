package br.com.usinasantafe.pcpcomp.presenter.residencia.motorista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MotoristaResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val motorista: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MotoristaResidenciaViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MotoristaResidenciaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onMotoristaChanged(motorista: String) {
        _uiState.update {
            it.copy(motorista = motorista)
        }
    }
    fun setMotorista() {
        if (uiState.value.motorista.isEmpty()) {
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