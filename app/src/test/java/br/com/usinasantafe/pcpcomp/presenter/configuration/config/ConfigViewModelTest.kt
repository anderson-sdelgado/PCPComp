package br.com.usinasantafe.pcpcomp.presenter.configuration.config
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocalTrab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateVisitante
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ConfigViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getConfigInternal = mock<GetConfigInternal>()
    private val sendDataConfig = mock<SendDataConfig>()
    private val saveDataConfig = mock<SaveDataConfig>()
    private val updateChave = mock<UpdateChave>()
    private val updateColab = mock<UpdateColab>()
    private val updateEquip = mock<UpdateEquip>()
    private val updateFluxo = mock<UpdateFluxo>()
    private val updateLocal = mock<UpdateLocal>()
    private val updateLocalTrab = mock<UpdateLocalTrab>()
    private val updateRLocalFluxo = mock<UpdateRLocalFluxo>()
    private val updateTerceiro = mock<UpdateTerceiro>()
    private val updateVisitante = mock<UpdateVisitante>()
    private val setCheckUpdateAllTable = mock<SetCheckUpdateAllTable>()
    private val sizeAll = 28f
    private var contWhenever = 0f
    private var contResult = 0f
    private var contUpdate = 0f

    private fun getViewModel() = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateChave = updateChave,
        updateColab = updateColab,
        updateEquip = updateEquip,
        updateFluxo = updateFluxo,
        updateLocal = updateLocal,
        updateLocalTrab = updateLocalTrab,
        updateRLocalFluxo = updateRLocalFluxo,
        updateTerceiro = updateTerceiro,
        updateVisitante = updateVisitante,
        setCheckUpdateAllTable = setCheckUpdateAllTable,
    )

    @Test
    fun `Check return null if don't have Config table internal`() = runTest {
        whenever(
            getConfigInternal()
        ).thenReturn(
            null
        )
        val viewModel = getViewModel()
        viewModel.returnDataConfig()
        assertEquals(
            viewModel.uiState.value.number,
            ""
        )
        assertEquals(
            viewModel.uiState.value.password,
            ""
        )
    }

    @Test
    fun `Check return data if have Config table internal`() = runTest {
        val configModel = ConfigModel(
            number = "16997417840",
            password = "12345"
        )
        whenever(
            getConfigInternal()
        ).thenReturn(
            Result.success(configModel)
        )
        val viewModel = getViewModel()
        viewModel.returnDataConfig()
        assertEquals(
            viewModel.uiState.value.number,
            "16997417840"
        )
        assertEquals(
            viewModel.uiState.value.password,
            "12345"
        )
    }

    @Test
    fun `Check return msg when field empty`() = runTest {
        val viewModel = getViewModel()
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.errors,
            Errors.FIELDEMPTY
        )
    }

    @Test
    fun `check return failure usecase when field number type is not type number`() = runTest {
        whenever(
            sendDataConfig(
                number = "16dfda",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SendDataConfig",
                    cause = NumberFormatException("For input string: \"1df52\"")
                )
            )
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16dfda")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.TOKEN,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> SendDataConfig -> java.lang.NumberFormatException: For input string: \"1df52\"",
                msgProgress = "Failure Usecase -> SendDataConfig -> java.lang.NumberFormatException: For input string: \"1df52\"",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> SendDataConfig -> java.lang.NumberFormatException: For input string: \"1df52\""
        )
    }

    @Test
    fun `Check return failure datasource if have errors in Datasource`() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SendDataConfig",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.TOKEN,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SendDataConfig -> java.lang.NullPointerException",
                msgProgress = "Failure Datasource -> SendDataConfig -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Datasource -> SendDataConfig -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `Check return failure usecase in save if have errors in Save data Usecase`() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.success(1)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveDataConfig",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = percentage(2f, 3f),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.TOKEN,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> SaveDataConfig -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> SaveDataConfig -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> SaveDataConfig -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `Check return finally sent and save Config`() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.success(1)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = percentage(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = percentage(2f, 3f),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Ajuste iniciais finalizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }
    @Test
    fun `Check return failure usecase if have error in usecase UpdateChave`() = runTest {
        whenever(
            updateChave(
                sizeAll = sizeAll,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(1f, sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_chave",
                currentProgress = percentage(1f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateColab`() = runTest {
        val qtdBefore = 1f
        wheneverSuccessChave()
        whenever(
            updateColab(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateEquip`() = runTest {
        val qtdBefore = 2f
        wheneverSuccessChave()
        wheneverSuccessColab()
        whenever(
            updateEquip(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateFluxo`() = runTest {
        val qtdBefore = 3f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        whenever(
            updateFluxo(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanFluxo -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanFluxo -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_fluxo",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanFluxo -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanFluxo -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateLocal`() = runTest {
        val qtdBefore = 4f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        whenever(
            updateLocal(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanLocal -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanLocal -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanLocal -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanLocal -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateLocalTrab`() = runTest {
        val qtdBefore = 5f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        whenever(
            updateLocalTrab(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local_trab",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateRLocalFluxo`() = runTest {
        val qtdBefore = 6f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        whenever(
            updateRLocalFluxo(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanRLocalFluxo -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanRLocalFluxo -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_r_local_fluxo",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanRLocalFluxo -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanRLocalFluxo -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateTerceiro`() = runTest {
        val qtdBefore = 7f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure usecase if have error in usecase UpdateVisitante`() = runTest {
        val qtdBefore = 8f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        whenever(
            updateVisitante(
                sizeAll = sizeAll,
                count = (qtdBefore + 1)
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        assertEquals(
            result[(qtdBefore * 3).toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[((qtdBefore * 3) + 1).toInt()],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
            )
        )
    }

    @Test
    fun `Check return failure datasource if have error in datasource SetCheckUpdateAllTable`() = runTest {
        val qtdBefore = 9f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(
            setCheckUpdateAllTable(FlagUpdate.UPDATED)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SetCheckUpdateAllTable",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 1).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        checkResultUpdateVisitante(result)
        assertEquals(
            result[27],
            ConfigState(
                errors = Errors.EXCEPTION,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SetCheckUpdateAllTable -> java.lang.Exception",
            )
        )
    }

    @Test
    fun `check return success if all update run correctly`() = runTest {
        val qtdBefore = 9f
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(
            setCheckUpdateAllTable(FlagUpdate.UPDATED)
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel()
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 1).toInt())
        checkResultUpdateChave(result)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateLocalTrab(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        checkResultUpdateVisitante(result)
        assertEquals(
            result[27],
            ConfigState(
                flagDialog = true,
                flagProgress = true,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return success if saveTokenAndUpdateAllDatabase is success`() = runTest {
        wheneverSuccessToken()
        wheneverSuccessChave()
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessLocalTrab()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        val viewModel = getViewModel()
        viewModel.onNumberChanged("16997417840")
        viewModel.onPasswordChanged("12345")
        viewModel.updateVersion("6.00")
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Atualização de dados realizado com sucesso!"
        )
    }

    private fun wheneverSuccessToken() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(
            Result.success(1)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(
            Result.success(true)
        )
    }

    private fun wheneverSuccessChave() =
        runTest {
            whenever(
                updateChave(
                    sizeAll = sizeAll,
                    count = ++contUpdate
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_chave",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_chave",
                        currentProgress = percentage(++contWhenever, sizeAll)
                    ),
                )
            )
        }

    private fun checkResultUpdateChave(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessColab() = runTest {
        whenever(
            updateColab(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateColab(result: List<ConfigState>) =
        runTest {
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
            assertEquals(
                result[contResult.toInt()],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(++contResult, sizeAll)
                )
            )
        }

    private fun wheneverSuccessEquip() = runTest {
        whenever(
            updateEquip(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateEquip(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_equip",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessFluxo() = runTest {
        whenever(
            updateFluxo(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateFluxo(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessLocal() = runTest {
        whenever(
            updateLocal(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateLocal(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessLocalTrab() = runTest {
        whenever(
            updateLocalTrab(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local_trab",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateLocalTrab(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local_trab",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local_trab",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessRLocalFluxo() = runTest {
        whenever(
            updateRLocalFluxo(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateRLocalFluxo(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_r_local_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessTerceiro() = runTest {
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateTerceiro(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

    private fun wheneverSuccessVisitante() = runTest {
        whenever(
            updateVisitante(
                sizeAll = sizeAll,
                count = ++contUpdate
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = percentage(++contWhenever, sizeAll)
                ),
            )
        )
    }

    private fun checkResultUpdateVisitante(result: List<ConfigState>) = runTest {
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
        assertEquals(
            result[contResult.toInt()],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = percentage(++contResult, sizeAll)
            )
        )
    }

}
