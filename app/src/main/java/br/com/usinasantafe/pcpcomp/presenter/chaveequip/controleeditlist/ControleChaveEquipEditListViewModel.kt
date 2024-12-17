package br.com.usinasantafe.pcpcomp.presenter.chaveequip.controleeditlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.CloseAllMovChaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.GetMovChaveEquipOpenList
import br.com.usinasantafe.pcpcomp.presenter.chaveequip.model.ControleChaveEquipModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ControleChaveEquipEditListState(
    val controleChaveEquipModelList: List<ControleChaveEquipModel> = listOf(),
    val flagDialogCheck: Boolean = false,
    val flagCloseAllMov: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ControleChaveEquipEditListViewModel(
    private val getMovChaveEquipOpenList: GetMovChaveEquipOpenList,
    private val closeAllMovChaveEquip: CloseAllMovChaveEquip,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveEquipEditListState())
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
        val resultCloseAllMov = closeAllMovChaveEquip()
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
        val resultGetList = getMovChaveEquipOpenList()
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
                controleChaveEquipModelList = result,
                flagDialog = false
            )
        }
    }
}