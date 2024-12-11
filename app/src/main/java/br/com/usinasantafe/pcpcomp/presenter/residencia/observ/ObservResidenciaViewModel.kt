package br.com.usinasantafe.pcpcomp.presenter.residencia.observ

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetObservResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SaveMovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetObservResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.StartOutputMovEquipResidencia
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ObservResidenciaState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val typeMov: TypeMovEquip = TypeMovEquip.INPUT,
    val observ: String? = null,
    val flagGetObserv: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ObservResidenciaViewModel(
    savedStateHandle: SavedStateHandle,
    private val setObservResidencia: SetObservResidencia,
    private val getObservResidencia: GetObservResidencia,
    private val startOutputMovEquipResidencia: StartOutputMovEquipResidencia,
    private val saveMovEquipResidencia: SaveMovEquipResidencia
) : ViewModel() {

    private val typeMov: Int = savedStateHandle[TYPE_MOV_ARGS]!!
    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ObservResidenciaState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeMov = TypeMovEquip.entries[typeMov],
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

    fun recoverObserv() = viewModelScope.launch {
        if(
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.flagGetObserv)
        ) {
            val resultGetObserv = getObservResidencia(
                id = uiState.value.id
            )
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
            val observ = resultGetObserv.getOrNull()
            _uiState.update {
                it.copy(
                    observ = observ,
                    flagGetObserv = false,
                )
            }
        }
    }

    fun setObserv() = viewModelScope.launch {
        if (
            (uiState.value.typeMov == TypeMovEquip.OUTPUT) &&
            (uiState.value.flowApp == FlowApp.ADD)
        ) {
            val resultStart = startOutputMovEquipResidencia(
                id = uiState.value.id
            )
            if(resultStart.isFailure) {
                val error = resultStart.exceptionOrNull()!!
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
        val resultSetObserv = setObservResidencia(
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
            val resultSaveMovEquip = saveMovEquipResidencia(
                typeMov = uiState.value.typeMov,
                id = uiState.value.id
            )
            if (resultSaveMovEquip.isFailure) {
                val error = resultSaveMovEquip.exceptionOrNull()!!
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
            )
        }
    }

}