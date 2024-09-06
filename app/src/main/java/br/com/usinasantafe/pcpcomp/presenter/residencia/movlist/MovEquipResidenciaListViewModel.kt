package br.com.usinasantafe.pcpcomp.presenter.residencia.movlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MovEquipResidenciaListState(
    val descrVigia: String = "",
    val descrLocal: String = "",
    val movEquipResidenciaModelList: List<MovEquipResidenciaModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipResidenciaListViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipResidenciaListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun startMov() {

    }


}