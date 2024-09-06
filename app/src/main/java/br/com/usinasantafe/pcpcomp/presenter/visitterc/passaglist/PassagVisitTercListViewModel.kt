package br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PassagVisitTercListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val passagList: List<PassagVisitTercModel> = emptyList(),
    val flagClean: Boolean = true,
    val idVisitTercSelected: Int? = null,
    val flagDialogCheck: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class PassagVisitTercListViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(PassagVisitTercListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setDelete(idVisitTerc: Int) {
        _uiState.update {
            it.copy(
                idVisitTercSelected = idVisitTerc,
                flagDialogCheck = true
            )
        }
    }

    fun setCloseDialogCheck() {
        _uiState.update {
            it.copy(flagDialogCheck = false)
        }
    }
    fun deletePassag() = viewModelScope.launch {

    }

}