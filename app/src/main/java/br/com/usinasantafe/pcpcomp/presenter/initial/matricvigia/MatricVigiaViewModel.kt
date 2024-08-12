package br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.ui.theme.addTextField
import br.com.usinasantafe.pcpcomp.ui.theme.clearTextField
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TB_COLAB
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MatricVigiaState(
    val matricVigia: String = "",
    val flagDialog: Boolean = false,
    val flagAccess: Boolean = false,
    val flagFailure: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELDEMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
    val currentProgress: Float = 0.0f,
)

class MatricVigiaViewModel(
    private val checkMatricColab: CheckMatricColab,
    private val setMatricVigiaConfig: SetMatricVigiaConfig,
    private val cleanColab: CleanColab,
    private val recoverColabServer: RecoverColabServer,
    private val saveAllColab: SaveAllColab,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatricVigiaState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(
                flagDialog = false
            )
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ){
        when(typeButton){
            TypeButton.NUMERIC -> {
                val matricVigia = addTextField(uiState.value.matricVigia, text)
                _uiState.update {
                    it.copy(matricVigia = matricVigia)
                }
            }
            TypeButton.CLEAN -> {
                val matricVigia = clearTextField(uiState.value.matricVigia)
                _uiState.update {
                    it.copy(matricVigia = matricVigia)
                }
            }
            TypeButton.OK -> {
                if (uiState.value.matricVigia.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELDEMPTY
                        )
                    }
                    return
                }
                viewModelScope.launch {
                    val resultCheckMatric = checkMatricColab(uiState.value.matricVigia)
                    if(resultCheckMatric.isFailure){
                        val error = resultCheckMatric.exceptionOrNull()!!
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
                    val result = resultCheckMatric.getOrNull()!!
                    val resultSetMatric = setMatricVigiaConfig(uiState.value.matricVigia)
                    if(resultSetMatric.isFailure){
                        val error = resultSetMatric.exceptionOrNull()!!
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
                    _uiState.update {
                        it.copy(
                            flagAccess = result,
                            flagDialog = !result,
                            flagFailure = !result,
                            errors = Errors.INVALID,
                        )
                    }
                }
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

    suspend fun updateAllDatabase(): Flow<MatricVigiaState> = flow {
        val sizeUpdate = 4f
        var configState = MatricVigiaState()
        updateAllColab(sizeUpdate, 1f).collect{ stateUpdateColab ->
            configState = stateUpdateColab
            emit(stateUpdateColab)
        }
        if(configState.flagFailure)
            return@flow
        emit(
            MatricVigiaState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    suspend fun updateAllColab(sizeAll: Float, count: Float): Flow<MatricVigiaState> = flow {
        emit(
            MatricVigiaState(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_COLAB",
                currentProgress = porc(1f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultClean = cleanColab()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                MatricVigiaState(
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
            MatricVigiaState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_COLAB do Web Service",
                currentProgress = porc(2f + ((count - 1) * 3), sizeAll),
            )
        )
        val resultRecover = recoverColabServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                MatricVigiaState(
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
            MatricVigiaState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_COLAB",
                currentProgress = porc(3f + ((count - 1) * 3), sizeAll),
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllColab(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                MatricVigiaState(
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