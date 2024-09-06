package br.com.usinasantafe.pcpcomp.presenter.visitterc.movlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipVisitTercListState(
    val movEquipVisitTercModelList: List<MovEquipVisitTercModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipVisitTercListViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipVisitTercListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun startMov() = viewModelScope.launch {

    }

    fun recoverMovEquipInputList() = viewModelScope.launch {

    }

}