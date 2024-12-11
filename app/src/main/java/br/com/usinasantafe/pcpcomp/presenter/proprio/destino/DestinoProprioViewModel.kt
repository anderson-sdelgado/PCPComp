package br.com.usinasantafe.pcpcomp.presenter.proprio.destino

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetDestinoProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetDestinoProprio
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DestinoProprioState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val destino: String = "",
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val flagGetDestino: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class DestinoProprioViewModel(
    savedStateHandle: SavedStateHandle,
    private val setDestinoProprio: SetDestinoProprio,
    private val getDestinoProprio: GetDestinoProprio,
    private val getTypeMov: GetTypeMov
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DestinoProprioState())
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

    fun onDestinoChanged(destino: String) {
        _uiState.update {
            it.copy(destino = destino)
        }
    }

    fun getDestino() = viewModelScope.launch {
        if (
            uiState.value.flowApp == FlowApp.CHANGE &&
            uiState.value.flagGetDestino
        ) {
            val resultGetDestino = getDestinoProprio(uiState.value.id)
            if (resultGetDestino.isFailure) {
                val error = resultGetDestino.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val destino = resultGetDestino.getOrNull()!!
            _uiState.update {
                it.copy(
                    destino = destino,
                    flagGetDestino = false
                )
            }
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
        viewModelScope.launch {
            val resultSetDestino = setDestinoProprio(
                uiState.value.destino,
                uiState.value.flowApp,
                uiState.value.id
            )
            if (resultSetDestino.isFailure) {
                val error = resultSetDestino.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val resultGetTypeMov = getTypeMov()
            if (resultGetTypeMov.isFailure) {
                val error = resultGetTypeMov.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val typeMov = resultGetTypeMov.getOrNull()!!
            _uiState.update {
                it.copy(
                    typeMov = typeMov,
                    flagAccess = true,
                )
            }
        }
    }

}