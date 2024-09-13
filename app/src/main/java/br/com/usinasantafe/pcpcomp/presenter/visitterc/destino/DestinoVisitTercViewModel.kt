package br.com.usinasantafe.pcpcomp.presenter.visitterc.destino

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetDestinoVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetDestinoVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DestinoVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val destino: String = "",
    val checkGetDestino: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class DestinoVisitTercViewModel(
    savedStateHandle: SavedStateHandle,
    private val setDestinoVisitTerc: SetDestinoVisitTerc,
    private val getDestinoVisitTerc: GetDestinoVisitTerc
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DestinoVisitTercState())
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

    fun recoverDestino() = viewModelScope.launch {
        if(
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.checkGetDestino)
        ) {
            val resultGetDestino = getDestinoVisitTerc(
                id = uiState.value.id
            )
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
                    checkGetDestino = false,
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
            val resultSetDestino = setDestinoVisitTerc(
                destino = uiState.value.destino,
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
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
            val flagAccess = resultSetDestino.getOrNull()!!
            _uiState.update {
                it.copy(
                    flagAccess = flagAccess,
                )
            }
        }
    }


}