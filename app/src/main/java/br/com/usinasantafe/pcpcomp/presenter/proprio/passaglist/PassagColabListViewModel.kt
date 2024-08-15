package br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CleanPassagColab
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.POS_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PassagColabListState(
    val passagList: List<Colab> = emptyList(),
    val matricColabSelected: Long? = null,
    val flagDialogCheck: Boolean = false,
    val flowApp: Int = 0,
    val typeOcupante: Int = 0,
    val pos: Int? = null,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class PassagColabListViewModel(
    saveStateHandle: SavedStateHandle,
    private val cleanPassagColab: CleanPassagColab,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val pos: Int = saveStateHandle[POS_ARGS]!!

    private val _uiState = MutableStateFlow(PassagColabListState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = flowApp,
                typeOcupante = typeOcupante,
                pos = pos
            )
        }

    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun cleanPassag() = viewModelScope.launch {
        val resultClean = cleanPassagColab()
        if(resultClean.isFailure){
            val error = resultClean.exceptionOrNull()!!
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
    }

    fun setPassagDelete(matricColab: Long) {
        _uiState.update {
            it.copy(
                matricColabSelected = matricColab,
                flagDialogCheck = true
            )
        }
    }

}