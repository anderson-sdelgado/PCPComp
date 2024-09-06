package br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
)

class DetalheVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalheVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverDetalhe() = viewModelScope.launch {

    }

}