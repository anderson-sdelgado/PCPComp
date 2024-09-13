package br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetTipoVisitTerc
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TipoVisitTercState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class TipoVisitTercViewModel(
    private val setTipoVisitTerc: SetTipoVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TipoVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTypeVisitTerc(typeVisitTerc: TypeVisitTerc) = viewModelScope.launch {
        val resultSetTypeVisitTerc = setTipoVisitTerc(typeVisitTerc)
        if (resultSetTypeVisitTerc.isFailure) {
            val error = resultSetTypeVisitTerc.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
            )
        }
    }

}