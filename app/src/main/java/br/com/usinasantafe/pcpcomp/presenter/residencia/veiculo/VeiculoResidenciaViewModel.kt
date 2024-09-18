package br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetVeiculoResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetVeiculoResidencia
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VeiculoResidenciaState (
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val veiculo: String = "",
    val checkGetVeiculo: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class VeiculoResidenciaViewModel(
    savedStateHandle: SavedStateHandle,
    private val getVeiculoResidencia: GetVeiculoResidencia,
    private val setVeiculoResidencia: SetVeiculoResidencia
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(VeiculoResidenciaState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

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

    fun recoverVeiculo() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetVeiculo)
        ) {
            val resultGetVeiculo = getVeiculoResidencia(
                id = uiState.value.id
            )
            if (resultGetVeiculo.isFailure) {
                val error = resultGetVeiculo.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val veiculo = resultGetVeiculo.getOrNull()!!
            _uiState.update {
                it.copy(
                    veiculo = veiculo,
                    checkGetVeiculo = false,
                )
            }
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
        viewModelScope.launch {
            val resultSetVeiculo = setVeiculoResidencia(
                veiculo = uiState.value.veiculo,
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
            if (resultSetVeiculo.isFailure) {
                val error = resultSetVeiculo.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            _uiState.update {
                it.copy(
                    flagAccess = true,
                )
            }
        }
    }


}