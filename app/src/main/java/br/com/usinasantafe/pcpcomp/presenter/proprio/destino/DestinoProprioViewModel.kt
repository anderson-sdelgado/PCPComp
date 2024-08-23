package br.com.usinasantafe.pcpcomp.presenter.proprio.destino

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetDestinoProprio
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DestinoProprioState(
    val destino: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val typeMov: TypeMov = TypeMov.INPUT,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class DestinoProprioViewModel(
    saveStateHandle: SavedStateHandle,
    private val setDestinoProprio: SetDestinoProprio,
    private val getTypeMov: GetTypeMov
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

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
                _uiState.value.flowApp,
                _uiState.value.id
            )
            if (resultSetDestino.isFailure){
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
            if (resultGetTypeMov.isFailure){
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