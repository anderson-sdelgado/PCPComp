package br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf

import androidx.lifecycle.ViewModel
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CpfVisitTercState (
    val flowApp: FlowApp = FlowApp.ADD,
    val cpf: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

class CpfVisitTercViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(CpfVisitTercState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {

    }

}