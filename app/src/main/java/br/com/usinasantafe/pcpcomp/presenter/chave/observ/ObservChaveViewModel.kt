package br.com.usinasantafe.pcpcomp.presenter.chave.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetObservMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SaveMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SetObservMovChave
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ObservChaveState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.REMOVE,
    val id: Int = 0,
    val observ: String? = null,
    val flagGetObserv: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ObservChaveViewModel(
    savedStateHandle: SavedStateHandle,
    private val setObservMovChave: SetObservMovChave,
    private val saveMovChave: SaveMovChave,
    private val getObservMovChave: GetObservMovChave
) : ViewModel() {

    private val typeMov: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservChaveState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeMov = TypeMovKey.entries[typeMov],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onObservChanged(observ: String) {
        _uiState.update {
            it.copy(observ = observ)
        }
    }

    fun getObserv() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.flagGetObserv)
        ) {
            val resultGetObserv = getObservMovChave(uiState.value.id)
            if (resultGetObserv.isFailure) {
                val error = resultGetObserv.exceptionOrNull()!!
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
                    observ = resultGetObserv.getOrNull(),
                    flagGetObserv = false
                )
            }
        }
    }

    fun setObserv() = viewModelScope.launch {
        val resultSetObserv = setObservMovChave(
            observ = uiState.value.observ,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id,
        )
        if(resultSetObserv.isFailure) {
            val error = resultSetObserv.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        if(uiState.value.flowApp == FlowApp.ADD){
            val resultSaveMov = saveMovChave(
                typeMov = uiState.value.typeMov,
                id = uiState.value.id
            )
            if (resultSaveMov.isFailure) {
                val error = resultSaveMov.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
                flagDialog = false
            )
        }
    }
}