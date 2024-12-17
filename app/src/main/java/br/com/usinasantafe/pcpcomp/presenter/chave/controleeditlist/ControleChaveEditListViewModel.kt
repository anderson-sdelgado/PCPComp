package br.com.usinasantafe.pcpcomp.presenter.chave.controleeditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.CloseAllMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetMovChaveOpenList
import br.com.usinasantafe.pcpcomp.presenter.chave.model.ControleChaveModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ControleChaveEditListState(
    val controleChaveModelList: List<ControleChaveModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ControleChaveEditListViewModel(
    private val getMovChaveOpenList: GetMovChaveOpenList,
    private val closeAllMovChave: CloseAllMovChave,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveEditListState())
    val uiState = _uiState.asStateFlow()

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

    fun closeAllMov() = viewModelScope.launch {
        val resultCloseAllMov = closeAllMovChave()
        if (resultCloseAllMov.isFailure) {
            val error = resultCloseAllMov.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagCloseAllMov = true,
                flagDialog = false,
            )
        }
    }

    fun recoverMovOpenList() = viewModelScope.launch {
        val resultGetList = getMovChaveOpenList()
        if (resultGetList.isFailure) {
            val error = resultGetList.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = resultGetList.getOrNull()!!
        _uiState.update {
            it.copy(
                controleChaveModelList = result,
                flagDialog = false
            )
        }
    }
}