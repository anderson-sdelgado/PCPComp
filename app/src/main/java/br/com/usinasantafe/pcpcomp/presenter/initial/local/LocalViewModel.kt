package br.com.usinasantafe.pcpcomp.presenter.initial.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetIdLocalConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.GetLocalList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LocalState(
    val locals: List<Local> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class LocalViewModel(
    private val getLocalList: GetLocalList,
    private val setIdLocalConfig: SetIdLocalConfig,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocalState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun startRecoverLocals() = viewModelScope.launch {
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

}