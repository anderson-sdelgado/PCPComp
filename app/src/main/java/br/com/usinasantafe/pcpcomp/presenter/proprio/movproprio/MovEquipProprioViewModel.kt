package br.com.usinasantafe.pcpcomp.presenter.proprio.movproprio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.common.RecoverHeader
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.RecoverMovEquipProprioOpenList
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.StartMovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovEquipProprioState(
    val movEquipProprioModels: List<MovEquipProprioModel> = emptyList(),
    val descrVigia: String = "",
    val descrLocal: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MovEquipProprioViewModel(
    private val recoverHeader: RecoverHeader,
    private val startMovEquipProprio: StartMovEquipProprio,
    private val recoverMovEquipProprioOpenList: RecoverMovEquipProprioOpenList,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovEquipProprioState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnHeader() = viewModelScope.launch {
        val recoverHeader = recoverHeader()
        if(recoverHeader.isFailure){
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

    fun recoverMovEquipOpenList() = viewModelScope.launch {
        val resultMovOpenList = recoverMovEquipProprioOpenList()
        if (resultMovOpenList.isFailure) {
            val error = resultMovOpenList.exceptionOrNull()!!
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
        val result = resultMovOpenList.getOrNull()!!
        _uiState.update {
            it.copy(
                movEquipProprioModels = result
            )
        }
    }

    fun startMovProprio(typeMov: TypeMov) = viewModelScope.launch {
        val resultStart = startMovEquipProprio(typeMov = typeMov)
        if(resultStart.isFailure){
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