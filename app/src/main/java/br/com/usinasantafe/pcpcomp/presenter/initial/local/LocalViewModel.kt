package br.com.usinasantafe.pcpcomp.presenter.initial.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetIdLocalConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.GetLocalList
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocal
import br.com.usinasantafe.pcpcomp.utils.Errors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LocalState(
    val locals: List<Local> = emptyList(),
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToLocal(): LocalState {
    return with(this){
        LocalState(
            flagDialog = this.flagDialog,
            flagFailure = this.flagFailure,
            errors = this.errors,
            failure = this.failure,
            flagProgress = this.flagProgress,
            msgProgress = this.msgProgress,
            currentProgress = this.currentProgress,
        )
    }
}

class LocalViewModel(
    private val getLocalList: GetLocalList,
    private val setIdLocalConfig: SetIdLocalConfig,
    private val updateLocal: UpdateLocal,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun localList() = viewModelScope.launch {
        val resultRecoverLocals = getLocalList()
        if (resultRecoverLocals.isFailure) {
            val error = resultRecoverLocals.exceptionOrNull()!!
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
        val result = resultRecoverLocals.getOrNull()!!
        _uiState.update {
            it.copy(
                locals = result
            )
        }
    }

    fun setIdLocal(id: Int) = viewModelScope.launch {
        val resultSetIdLocalConfig = setIdLocalConfig(id)
        if (resultSetIdLocalConfig.isFailure) {
            val error = resultSetIdLocalConfig.exceptionOrNull()!!
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
        val result = resultSetIdLocalConfig.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
            )
        }
    }

    fun updateDatabase() = viewModelScope.launch {
        viewModelScope.launch {
            updateAllDatabase().collect { stateUpdate ->
                _uiState.value = stateUpdate
            }
        }
    }

    suspend fun updateAllDatabase(): Flow<LocalState> = flow {
        val sizeUpdate = 4f
        var state = LocalState()
        updateLocal(sizeUpdate, 1f).collect {
            state = it.resultUpdateToLocal()
            emit(it.resultUpdateToLocal())
        }
        if (state.flagFailure)
            return@flow
        emit(
            LocalState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}