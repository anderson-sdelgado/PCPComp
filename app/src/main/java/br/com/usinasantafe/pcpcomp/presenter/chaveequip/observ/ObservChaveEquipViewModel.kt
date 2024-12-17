package br.com.usinasantafe.pcpcomp.presenter.chaveequip.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.GetObservMovChaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.SaveMovChaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.SetObservMovChaveEquip
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ObservChaveEquipState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeMov: TypeMovKey = TypeMovKey.RECEIPT,
    val id: Int = 0,
    val observ: String? = null,
    val flagGetObserv: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ObservChaveEquipViewModel(
    savedStateHandle: SavedStateHandle,
    private val setObservMovChaveEquip: SetObservMovChaveEquip,
    private val saveMovChaveEquip: SaveMovChaveEquip,
    private val getObservMovChaveEquip: GetObservMovChaveEquip
) : ViewModel() {

    private val typeMov: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservChaveEquipState())
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
            val resultGetObserv = getObservMovChaveEquip(uiState.value.id)
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
        val resultSetObserv = setObservMovChaveEquip(
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
            val resultSaveMov = saveMovChaveEquip(
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