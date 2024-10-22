package br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.CloseMovResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetDetalheResidencia
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalheResidenciaState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val veiculo: String = "",
    val placa: String = "",
    val motorista: String = "",
    val observ: String? = null,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false
)

class DetalheResidenciaViewModel(
    saveStateHandle: SavedStateHandle,
    private val getDetalheResidencia: GetDetalheResidencia,
    private val closeMovResidencia: CloseMovResidencia
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheResidenciaState())
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
        val resultRecoverDetalhe = getDetalheResidencia(uiState.value.id)
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
                motorista = detalhe.motorista,
                observ = detalhe.observ
            )
        }
    }

    fun closeMov() = viewModelScope.launch {
        val resultCloseMov = closeMovResidencia(id)
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