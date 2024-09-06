package br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo

import androidx.lifecycle.ViewModel
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TipoVisitTercState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class TipoVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTypeVisitTerc(typeVisitTerc: TypeVisitTerc) {
    }

}