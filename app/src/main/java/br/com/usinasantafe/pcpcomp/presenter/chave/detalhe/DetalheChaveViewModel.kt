package br.com.usinasantafe.pcpcomp.presenter.chave.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.CloseMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetDetalheMovChave
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalheChaveState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val chave: String = "",
    val colab: String = "",
    val observ: String? = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = ""
)

class DetalheChaveViewModel(
    saveStateHandle: SavedStateHandle,
    private val getDetalheMovChave: GetDetalheMovChave,
    private val closeMovChave: CloseMovChave
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheChaveState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                id = id
            )
        }
    }

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

    fun recoverDetalhe() = viewModelScope.launch {
        val resultRecover = getDetalheMovChave(uiState.value.id)
        if(resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val detalhe = resultRecover.getOrNull()!!
        _uiState.update {
            it.copy(
                dthr = detalhe.dthr,
                tipoMov = detalhe.tipoMov,
                chave = detalhe.chave,
                colab = detalhe.colab,
                observ = detalhe.observ
            )
        }
    }

    fun closeMov() = viewModelScope.launch {
        val resultCloseMov = closeMovChave(uiState.value.id)
        if(resultCloseMov.isFailure) {
            val error = resultCloseMov.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val result = resultCloseMov.getOrNull()!!
        _uiState.update {
            it.copy(
                flagDialogCheck = false,
                flagCloseMov = result
            )
        }
    }

}