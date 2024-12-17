package br.com.usinasantafe.pcpcomp.presenter.chaveequip.detalhe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.CloseMovChaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.GetDetalheMovChaveEquip
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalheChaveEquipState(
    val id: Int = 0,
    val dthr: String = "",
    val tipoMov: String = "",
    val equip: String = "",
    val colab: String = "",
    val observ: String? = "",
    val flagDialogCheck: Boolean = false,
    val flagCloseMov: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = ""
)

class DetalheChaveEquipViewModel(
    saveStateHandle: SavedStateHandle,
    private val getDetalheMovChaveEquip: GetDetalheMovChaveEquip,
    private val closeMovChaveEquip: CloseMovChaveEquip
) : ViewModel() {

    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(DetalheChaveEquipState())
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
        val resultRecover = getDetalheMovChaveEquip(uiState.value.id)
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
                equip = detalhe.equip,
                colab = detalhe.colab,
                observ = detalhe.observ
            )
        }
    }

    fun closeMov() = viewModelScope.launch {
        val resultCloseMov = closeMovChaveEquip(uiState.value.id)
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