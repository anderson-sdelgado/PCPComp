package br.com.usinasantafe.pcpcomp.presenter.chaveequip.nroequip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.GetNroEquipChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip.SetNroEquipChave
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetNroEquipProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateEquip
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.NroEquipProprioState
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.resultUpdateToNroEquipProprio
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NroEquipChaveState(
    val nroEquip: String = "",
    val flagGetNro: Boolean = true,
    val flowApp: FlowApp = FlowApp.ADD,
    val id: Int = 0,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

fun ResultUpdate.resultUpdateToNroEquipChave(): NroEquipChaveState {
    return with(this){
        NroEquipChaveState(
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

class NroEquipChaveViewModel(
    saveStateHandle: SavedStateHandle,
    private val checkNroEquip: CheckNroEquip,
    private val setNroEquipChave: SetNroEquipChave,
    private val updateEquip: UpdateEquip,
    private val getNroEquipChave: GetNroEquipChave,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NroEquipChaveState())
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
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val nroEquip = addTextField(uiState.value.nroEquip, text)
                _uiState.update {
                    it.copy(nroEquip = nroEquip)
                }
            }

            TypeButton.CLEAN -> {
                val nroEquip = clearTextField(uiState.value.nroEquip)
                _uiState.update {
                    it.copy(nroEquip = nroEquip)
                }
            }

            TypeButton.OK -> {
                if (uiState.value.nroEquip.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                setNroEquip()
            }

            TypeButton.UPDATE -> {
                viewModelScope.launch {
                    updateAllDatabase().collect { stateUpdate ->
                        _uiState.value = stateUpdate
                    }
                }
            }
        }
    }

    private fun setNroEquip() = viewModelScope.launch {
        val resultCheckEquip = checkNroEquip(uiState.value.nroEquip)
        if (resultCheckEquip.isFailure) {
            val error = resultCheckEquip.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                )
            }
            return@launch
        }
        val result = resultCheckEquip.getOrNull()!!
        if (result) {
            val resultSetEquip = setNroEquipChave(
                nroEquip = uiState.value.nroEquip,
                flowApp = uiState.value.flowApp,
                id = uiState.value.id
            )
            if (resultSetEquip.isFailure) {
                val error = resultSetEquip.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                    )
                }
                return@launch
            }
        }
        _uiState.update {
            it.copy(
                flagAccess = result,
                flagDialog = !result,
                flagFailure = !result,
                errors = Errors.INVALID,
            )
        }
    }

    suspend fun updateAllDatabase(): Flow<NroEquipChaveState> = flow {
        val sizeUpdate = 4f
        var configState = NroEquipChaveState()
        updateEquip(sizeUpdate, 1f).collect {
            configState = it.resultUpdateToNroEquipChave()
            emit(it.resultUpdateToNroEquipChave())
        }
        if (configState.flagFailure)
            return@flow
        emit(
            NroEquipChaveState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    fun getNroEquip() = viewModelScope.launch {
        if (
            (uiState.value.flowApp == FlowApp.CHANGE) &&
            (uiState.value.flagGetNro)
        ) {
            val resultGetNro = getNroEquipChave(uiState.value.id)
            if (resultGetNro.isFailure) {
                val error = resultGetNro.exceptionOrNull()!!
                val failure =
                    "${error.message} -> ${error.cause.toString()}"
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        flagFailure = true,
                        errors = Errors.EXCEPTION,
                        failure = failure,
                    )
                }
                return@launch
            }
            val nroEquip = resultGetNro.getOrNull()!!
            _uiState.update {
                it.copy(
                    nroEquip = nroEquip,
                    flagGetNro = false
                )
            }
        }
    }
}