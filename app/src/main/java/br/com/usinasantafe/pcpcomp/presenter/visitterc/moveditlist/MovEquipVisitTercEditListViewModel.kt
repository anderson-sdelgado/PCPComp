package br.com.usinasantafe.pcpcomp.presenter.visitterc.moveditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CloseAllMovVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetMovEquipVisitTercOpenList
import br.com.usinasantafe.pcpcomp.presenter.visitterc.model.MovEquipVisitTercModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipVisitTercEditListState(
    val movEquipVisitTercModelList: List<MovEquipVisitTercModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipVisitTercEditListViewModel(
    private val getMovEquipVisitTercOpenList: GetMovEquipVisitTercOpenList,
    private val closeAllMovVisitTerc: CloseAllMovVisitTerc,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipVisitTercEditListState())
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
        val resultCloseAllMov = closeAllMovVisitTerc()
        if(resultCloseAllMov.isFailure) {
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
            )
        }
    }

    fun recoverMovEquipEditList() = viewModelScope.launch {
        val resultGetList = getMovEquipVisitTercOpenList()
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
                movEquipVisitTercModelList = result
            )
        }
    }

}