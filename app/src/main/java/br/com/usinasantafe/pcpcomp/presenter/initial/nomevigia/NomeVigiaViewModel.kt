package br.com.usinasantafe.pcpcomp.presenter.initial.nomevigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.RecoverNomeVigia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NomeVigiaState(
    val nomeVigia: String = "",
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class NomeVigiaViewModel(
    private val recoverNomeVigia: RecoverNomeVigia
) : ViewModel() {

    private val _uiState = MutableStateFlow(NomeVigiaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnNomeVigia() = viewModelScope.launch {
        val recoverNome = recoverNomeVigia()
        if(recoverNome.isFailure){
            val error = recoverNome.exceptionOrNull()!!
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
        val result = recoverNome.getOrNull()!!
        _uiState.update {
            it.copy(
                nomeVigia = result
            )
        }
    }

}