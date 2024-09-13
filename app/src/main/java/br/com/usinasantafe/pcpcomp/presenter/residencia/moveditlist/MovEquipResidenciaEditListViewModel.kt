package br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipResidenciaEditListState(
    val movEquipResidenciaEditModelList: List<MovEquipResidenciaEditModel> = listOf(),
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipResidenciaEditListViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipResidenciaEditListState())
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