package br.com.usinasantafe.pcpcomp.presenter.visitterc.moveditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipVisitTercEditListState(
    val movEquipVisitTercEditModelList: List<MovEquipVisitTercEditModel> = listOf(),
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipVisitTercEditListViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipVisitTercEditListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun closeAllMov() {

    }

    fun recoverMovEquipEditList() = viewModelScope.launch {

    }

}