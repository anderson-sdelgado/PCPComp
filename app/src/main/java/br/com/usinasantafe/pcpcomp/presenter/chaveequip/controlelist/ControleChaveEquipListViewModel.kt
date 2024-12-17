package br.com.usinasantafe.pcpcomp.presenter.chaveequip.controlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.GetMovChaveEquipInsideList
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.StartReceiptMovChaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcpcomp.presenter.chaveequip.model.ControleChaveEquipModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ControleChaveEquipListState(
    val controleChaveEquipModelList: List<ControleChaveEquipModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class ControleChaveEquipListViewModel(
    private val getHeader: GetHeader,
    private val getMovChaveEquipInsideList: GetMovChaveEquipInsideList,
    private val startReceiptMovChaveEquip: StartReceiptMovChaveEquip
) : ViewModel() {

    private val _uiState = MutableStateFlow(ControleChaveEquipListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnHeader() = viewModelScope.launch {
        val recoverHeader = getHeader()
        if (recoverHeader.isFailure) {
            val error = recoverHeader.exceptionOrNull()!!
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
        val result = recoverHeader.getOrNull()!!
        _uiState.update {
            it.copy(
                descrVigia = result.descrVigia,
                descrLocal = result.descrLocal,
            )
        }
    }

    fun recoverMovList() = viewModelScope.launch {
        val resultGetList = getMovChaveEquipInsideList()
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
                controleChaveEquipModelList = result
            )
        }
    }

    fun startMov() = viewModelScope.launch {
        val resultStart = startReceiptMovChaveEquip()
        if (resultStart.isFailure) {
            val error = resultStart.exceptionOrNull()!!
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
                flagAccess = true,
                flagDialog = false,
            )
        }
    }
}