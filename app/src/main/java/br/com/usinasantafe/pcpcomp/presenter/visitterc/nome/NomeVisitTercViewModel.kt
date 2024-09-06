package br.com.usinasantafe.pcpcomp.presenter.visitterc.nome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NomeVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val cpdVisitTerc: String = "",
    val nomeVisitTerc: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class NomeVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(NomeVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setCPF() = viewModelScope.launch {
    }
}