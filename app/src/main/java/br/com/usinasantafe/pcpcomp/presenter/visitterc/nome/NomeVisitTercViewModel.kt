package br.com.usinasantafe.pcpcomp.presenter.visitterc.nome

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetNomeVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetCpfVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args.CPF_VISIT_TERC_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NomeVisitTercState(
    val flowApp: FlowApp = FlowApp.ADD,
    val typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
    val id: Int = 0,
    val tipo: String = "",
    val cpf: String = "",
    val nome: String = "",
    val empresa: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

class NomeVisitTercViewModel(
    saveStateHandle: SavedStateHandle,
    private val getNomeVisitTerc: GetNomeVisitTerc,
    private val setCpfVisitTerc: SetCpfVisitTerc,
) : ViewModel() {

    private val cpf: String = saveStateHandle[CPF_VISIT_TERC_ARGS]!!
    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeOcupante: Int = saveStateHandle[TYPE_OCUPANTE_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NomeVisitTercState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeOcupante = TypeOcupante.entries[typeOcupante],
                id = id,
                cpf = cpf
            )
        }
    }

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun returnNome() = viewModelScope.launch {
        val resultGetNome = getNomeVisitTerc(
            cpf = uiState.value.cpf,
            flowApp = uiState.value.flowApp,
            typeOcupante = uiState.value.typeOcupante,
            id = uiState.value.id
        )
        if (resultGetNome.isFailure) {
            val error = resultGetNome.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val nomeVisitTerc = resultGetNome.getOrNull()!!
        _uiState.update {
            it.copy(
                tipo = nomeVisitTerc.tipo,
                nome = nomeVisitTerc.nome,
                empresa = nomeVisitTerc.empresa,
            )
        }
    }

    fun setCPF() = viewModelScope.launch {
        val resultSetCpf = setCpfVisitTerc(
            cpf = uiState.value.cpf,
            flowApp = uiState.value.flowApp,
            typeOcupante = uiState.value.typeOcupante,
            id = uiState.value.id
        )
        if (resultSetCpf.isFailure) {
            val error = resultSetCpf.exceptionOrNull()!!
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