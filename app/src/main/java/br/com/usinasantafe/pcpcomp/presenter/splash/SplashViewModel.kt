package br.com.usinasantafe.pcpcomp.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.AdjustConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.CheckMovOpen
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.DeleteMovSent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SplashState(
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagAccess: Boolean = false,
    val flagMovOpen: Boolean = false,
)

class SplashViewModel(
    private val adjustConfig: AdjustConfig,
    private val deleteMovSent: DeleteMovSent,
    private val checkMovOpen: CheckMovOpen
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setOpenDialog() {
        _uiState.update {
            it.copy(flagDialog = true)
        }
    }

    fun processInitial(version: String) = viewModelScope.launch {
        val resultAdjustConfig = adjustConfig(version)
        if (resultAdjustConfig.isFailure) {
            val error = resultAdjustConfig.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val resultDeleteMovSent = deleteMovSent()
        if (resultDeleteMovSent.isFailure) {
            val error = resultDeleteMovSent.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val resultCheckMovOpen = checkMovOpen()
        if (resultCheckMovOpen.isFailure) {
            val error = resultCheckMovOpen.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val result = resultCheckMovOpen.getOrNull()!!
        _uiState.update {
            it.copy(
                flagAccess = true,
                flagMovOpen = result
            )
        }
    }

}