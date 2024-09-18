package br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.CloseAllMovResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetMovEquipResidenciaOpenList
import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipResidenciaEditListState(
    val movEquipResidenciaModelList: List<MovEquipResidenciaModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipResidenciaEditListViewModel(
    private val getMovEquipResidenciaOpenList: GetMovEquipResidenciaOpenList,
    private val closeAllMovResidencia: CloseAllMovResidencia,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipResidenciaEditListState())
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
        val resultCloseAllMov = closeAllMovResidencia()
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
            )
        }
    }

    fun recoverMovEquipEditList() = viewModelScope.launch {
        val resultGetList = getMovEquipResidenciaOpenList()
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
                movEquipResidenciaModelList = result
            )
        }
    }


}