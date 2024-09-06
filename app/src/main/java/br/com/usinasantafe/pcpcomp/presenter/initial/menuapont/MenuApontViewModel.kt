package br.com.usinasantafe.pcpcomp.presenter.initial.menuapont

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CloseAllMovOpen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MenuApontState(
    val descrVigia: String = "",
    val descrLocal: String = "",
    val flagDialogCheck: Boolean = false,
    val flagFinishCloseMov: Boolean = false,
    val flagReturn: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class MenuApontViewModel(
    private val getHeader: GetHeader,
    private val closeAllMovOpen: CloseAllMovOpen,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuApontState())
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


    fun returnHeader() = viewModelScope.launch {
        val resultRecoverHeader = getHeader()
        if(resultRecoverHeader.isFailure){
            val error = resultRecoverHeader.exceptionOrNull()!!
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
        val result = resultRecoverHeader.getOrNull()!!
        _uiState.update {
            it.copy(
                descrVigia = result.descrVigia,
                descrLocal = result.descrLocal,
            )
        }
    }

    fun closeAllMov() = viewModelScope.launch {
        val resultCloseAllMovOpen = closeAllMovOpen()
        if(resultCloseAllMovOpen.isFailure){
            val error = resultCloseAllMovOpen.exceptionOrNull()!!
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
        val result = resultCloseAllMovOpen.getOrNull()!!
        _uiState.update {
            it.copy(
                flagDialogCheck = false,
                flagReturn = result
            )
        }
    }

}