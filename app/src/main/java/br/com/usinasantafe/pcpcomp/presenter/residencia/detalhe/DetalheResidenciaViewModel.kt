package br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val observ: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class DetalheResidenciaViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalheResidenciaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverDetalhe() = viewModelScope.launch {

    }

}