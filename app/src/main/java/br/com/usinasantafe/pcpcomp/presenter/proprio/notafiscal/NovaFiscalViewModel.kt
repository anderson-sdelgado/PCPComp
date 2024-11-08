package br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetNotaFiscalProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetNotaFiscalProprio
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeButtonWithoutUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotaFiscalState(
    val notaFiscal: String? = null,
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val flagGetNotaFiscal: Boolean = true,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class NotaFiscalViewModel(
    savedStateHandle: SavedStateHandle,
    private val setNotaFiscalProprio: SetNotaFiscalProprio,
    private val getNotaFiscalProprio: GetNotaFiscalProprio
) : ViewModel() {

    private val flowApp: Int = savedStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = savedStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NotaFiscalState())
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

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        val nf = if (uiState.value.notaFiscal.isNullOrEmpty()) "" else uiState.value.notaFiscal!!
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val notaFiscal = addTextField(nf, text)
                _uiState.update {
                    it.copy(notaFiscal = notaFiscal)
                }
            }

            TypeButton.CLEAN -> {
                val notaFiscal = clearTextField(nf)
                _uiState.update {
                    it.copy(notaFiscal = notaFiscal)
                }
            }

            TypeButton.OK -> {
                setNotaFiscal()
            }

            TypeButton.UPDATE -> {}
        }
    }

    fun getNotaFiscal() = viewModelScope.launch {
        if (
            uiState.value.flowApp == FlowApp.CHANGE &&
            uiState.value.flagGetNotaFiscal
        ) {
            val resultSetNotaFiscal = getNotaFiscalProprio(uiState.value.id)
            if (resultSetNotaFiscal.isFailure) {
                val error = resultSetNotaFiscal.exceptionOrNull()!!
                val failure = "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        failure = failure,
                    )
                }
                return@launch
            }
            val notaFiscal = resultSetNotaFiscal.getOrNull()
            _uiState.update {
                it.copy(
                    notaFiscal = notaFiscal,
                    flagGetNotaFiscal = false
                )
            }
        }
    }

    private fun setNotaFiscal() = viewModelScope.launch {
        val resultSetNotaFiscal = setNotaFiscalProprio(
            uiState.value.notaFiscal,
            uiState.value.flowApp,
            uiState.value.id
        )
        if (resultSetNotaFiscal.isFailure) {
            val error = resultSetNotaFiscal.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
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
            )
        }
    }

}