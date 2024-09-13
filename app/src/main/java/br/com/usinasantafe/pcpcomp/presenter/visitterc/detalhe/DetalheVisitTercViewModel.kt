package br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CloseMovVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetDetalheVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalheVisitTercState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val veiculo: String = "",
    val placa: String = "",
    val tipoVisitTerc: String = "",
    val motorista: String = "",
    val passageiro: String = "",
    val destino: String = "",
    val observ: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false
)

class DetalheVisitTercViewModel(
    saveStateHandle: SavedStateHandle,
    private val getDetalheVisitTerc: GetDetalheVisitTerc,
    private val closeMovVisitTerc: CloseMovVisitTerc
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheVisitTercState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setDialogCheck(flagDialogCheck: Boolean) {
        _uiState.update {
            it.copy(flagDialogCheck = flagDialogCheck)
        }
    }

    fun recoverDetalhe() = viewModelScope.launch {
        val resultRecoverDetalhe = getDetalheVisitTerc(uiState.value.id)
        if(resultRecoverDetalhe.isFailure) {
            val error = resultRecoverDetalhe.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val detalhe = resultRecoverDetalhe.getOrNull()!!
        _uiState.update {
            it.copy(
                dthr = detalhe.dthr,
                tipoMov = detalhe.tipoMov,
                veiculo = detalhe.veiculo,
                placa = detalhe.placa,
                tipoVisitTerc = detalhe.tipoVisitTerc,
                motorista = detalhe.motorista,
                passageiro = detalhe.passageiro,
                destino = detalhe.destino,
                observ = detalhe.observ
            )
        }
    }

    fun closeMov() = viewModelScope.launch {
        val resultCloseMov = closeMovVisitTerc(id)
        if(resultCloseMov.isFailure) {
            val error = resultCloseMov.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val result = resultCloseMov.getOrNull()!!
        _uiState.update {
            it.copy(
                flagDialogCheck = false,
                flagCloseMov = result
            )
        }
    }
}