package br.com.usinasantafe.pcpcomp.presenter.chave.chavelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetChaveList
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SetIdChaveMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocalTrab
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChaveListState(
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val flagFilter: Boolean = false,
    val chaveList: List<ChaveModel> = emptyList(),
    val field: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToState(): ChaveListState {
    return with(this){
        ChaveListState(
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

class ChaveListViewModel(
    savedStateHandle: SavedStateHandle,
    private val getChaveList: GetChaveList,
    private val setIdChaveMovChave: SetIdChaveMovChave,
    private val updateChave: UpdateChave,
    private val updateLocalTrab: UpdateLocalTrab
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(ChaveListState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                id = id
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onFieldChanged(field: String) {
        val fieldUpper = field.uppercase()
        if(fieldUpper.isNotEmpty()){
            val chaveListFilter = _uiState.value.chaveList.filter {
                it.descr.contains(fieldUpper)
            }
            _uiState.update {
                it.copy(
                    chaveList = chaveListFilter,
                    flagFilter = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    flagFilter = false
                )
            }
            recoverList()
        }
        _uiState.update {
            it.copy(
                field = fieldUpper
            )
        }
    }

    fun recoverList() = viewModelScope.launch {
        if(!uiState.value.flagFilter) {
            val resultList = getChaveList()
            if (resultList.isFailure) {
                val error = resultList.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        errors = Errors.EXCEPTION,
                        flagDialog = true,
                        flagFailure = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val result = resultList.getOrNull()!!
            _uiState.update {
                it.copy(
                    chaveList = result
                )
            }
        }
    }

    fun setIdChave(id: Int) = viewModelScope.launch {
        val resultSet = setIdChaveMovChave(
            idChave = id,
            flowApp = uiState.value.flowApp,
            id = uiState.value.id
        )
        if (resultSet.isFailure) {
            val error = resultSet.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    errors = Errors.EXCEPTION,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true
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

    suspend fun updateAllDatabase(): Flow<ChaveListState> = flow {
        val sizeUpdate = 7f
        var chaveListState = ChaveListState()
        updateChave(sizeUpdate, 1f).collect {
            chaveListState = it.resultUpdateToState()
            emit(it.resultUpdateToState())
        }
        if (chaveListState.flagFailure)
            return@flow
        updateLocalTrab(sizeUpdate, 2f).collect {
            chaveListState = it.resultUpdateToState()
            emit(it.resultUpdateToState())
        }
        if (chaveListState.flagFailure)
            return@flow
        emit(
            ChaveListState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

}