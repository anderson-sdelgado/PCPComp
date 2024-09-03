package br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CheckNroEquipProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetNroEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetNroEquipProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverEquipServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllEquip
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TB_EQUIP
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NroEquipProprioState(
    val nroEquip: String = "",
    val flowApp: FlowApp = FlowApp.ADD,
    val typeEquip: TypeEquip = TypeEquip.VEICULO,
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

class NroEquipProprioViewModel(
    saveStateHandle: SavedStateHandle,
    private val checkNroEquipProprio: CheckNroEquipProprio,
    private val setNroEquipProprio: SetNroEquipProprio,
    private val cleanEquip: CleanEquip,
    private val recoverEquipServer: RecoverEquipServer,
    private val saveAllEquip: SaveAllEquip,
    private val getNroEquip: GetNroEquip,
) : ViewModel() {

    private val flowApp: Int = saveStateHandle[FLOW_APP_ARGS]!!
    private val typeEquip: Int = saveStateHandle[TYPE_EQUIP_ARGS]!!
    private val id: Int = saveStateHandle[ID_ARGS]!!

    private val _uiState = MutableStateFlow(NroEquipProprioState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                flowApp = FlowApp.entries[flowApp],
                typeEquip = TypeEquip.entries[typeEquip],
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

    fun getNroEquip() = viewModelScope.launch {
       val resultGetNro = getNroEquip(uiState.value.id)
        if(resultGetNro.isFailure) {
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
            it.copy(nroEquip = nroEquip)
        }
    }

    private fun setNroEquip() = viewModelScope.launch {
        val resultCheckEquip = checkNroEquipProprio(uiState.value.nroEquip)
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
            val resultSetEquip = setNroEquipProprio(
                nroEquip = uiState.value.nroEquip,
                flowApp = uiState.value.flowApp,
                typeEquip = uiState.value.typeEquip,
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

    suspend fun updateAllDatabase(): Flow<NroEquipProprioState> = flow {
        val sizeUpdate = 4f
        var configState = NroEquipProprioState()
        updateAllEquip(sizeUpdate, 1f).collect { stateUpdateColab ->
            configState = stateUpdateColab
            emit(stateUpdateColab)
        }
        if (configState.flagFailure)
            return@flow
        emit(
            NroEquipProprioState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllEquip(sizeAll: Float, count: Float): Flow<NroEquipProprioState> = flow {
        emit(
            NroEquipProprioState(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_EQUIP",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanEquip()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                NroEquipProprioState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            NroEquipProprioState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_EQUIP do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = recoverEquipServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                NroEquipProprioState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            NroEquipProprioState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_EQUIP",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllEquip(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                NroEquipProprioState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    flagProgress = false,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

}