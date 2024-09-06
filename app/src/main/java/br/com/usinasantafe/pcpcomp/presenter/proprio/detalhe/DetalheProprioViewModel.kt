package br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetDetalheProprio
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalheProprioState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val veiculo: String = "",
    val motorista: String = "",
    val passageiro: String = "",
    val destino: String = "",
    val veiculoSec: String = "",
    val notaFiscal: String = "",
    val observ: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class DetalheProprioViewModel(
    saveStateHandle: SavedStateHandle,
    private val getDetalheProprio: GetDetalheProprio
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheProprioState())
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

    fun recoverDetalhe() = viewModelScope.launch {
        val resultRecoverDetalhe = getDetalheProprio(id)
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
                motorista = detalhe.motorista,
                passageiro = detalhe.passageiro,
                destino = detalhe.destino,
                veiculoSec = detalhe.veiculoSec,
                notaFiscal = detalhe.notaFiscal,
                observ = detalhe.observ
            )
        }
    }


}