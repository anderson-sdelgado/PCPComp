package br.com.usinasantafe.pcpcomp.presenter.residencia.placa

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetPlacaResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetPlacaResidencia
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlacaResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val placa: String = "",
    val checkGetPlaca: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class PlacaResidenciaViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPlacaResidencia: GetPlacaResidencia,
    private val setPlacaResidencia: SetPlacaResidencia
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(PlacaResidenciaState())
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

    fun onPlacaChanged(placa: String) {
        if (placa.length <= 7) {
            _uiState.update {
                it.copy(placa = placa)
            }
        }
    }

    fun recoverPlaca() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetPlaca)
        ) {
            val resultGetPlaca = getPlacaResidencia(id = uiState.value.id)
            if (resultGetPlaca.isFailure) {
                val error = resultGetPlaca.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val placa = resultGetPlaca.getOrNull()!!
            _uiState.update {
                it.copy(
                    placa = placa,
                    checkGetPlaca = false,
                )
            }
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
        viewModelScope.launch {
            val resultSetPlaca = setPlacaResidencia(
                placa = uiState.value.placa,
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
            if (resultSetPlaca.isFailure) {
                val error = resultSetPlaca.exceptionOrNull()!!
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