package br.com.usinasantafe.pcpcomp.presenter.configuration.config
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.config.GetConfigInternal
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.colabList
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.equipList
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllEquipServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllFluxoServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllLocalServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllRLocalFluxoServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllTerceiroServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllVisitanteServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.localList
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.terceiroList
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.visitanteList
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
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
    private val updateColab = mock<UpdateColab>()
    private val updateEquip = mock<UpdateEquip>()
    private val updateFluxo = mock<UpdateFluxo>()
    private val updateLocal = mock<UpdateLocal>()
    private val updateRLocalFluxo = mock<UpdateRLocalFluxo>()
    private val updateTerceiro = mock<UpdateTerceiro>()
    private val updateVisitante = mock<UpdateVisitante>()
    private val setCheckUpdateAllTable = mock<SetCheckUpdateAllTable>()


    private fun getViewModel() = ConfigViewModel(
        getConfigInternal = getConfigInternal,
        sendDataConfig = sendDataConfig,
        saveDataConfig = saveDataConfig,
        updateColab = updateColab,
        updateEquip = updateEquip,
        updateFluxo = updateFluxo,
        updateLocal = updateLocal,
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
    fun `Check return failure usecase if have error in usecase UpdateColab`() = runTest {
        whenever(
            updateColab(
                sizeAll = 22f,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 22f)
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
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(1f, 22f)
            )
        )
        assertEquals(
            result[1],
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
        wheneverSuccessColab()
        whenever(
            updateEquip(
                sizeAll = 22f,
                count = 2f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(4f, 22f)
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
        assertEquals(result.count(), 5)
        checkResultUpdateColab(result)
        assertEquals(
            result[3],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(4f, 22f)
            )
        )
        assertEquals(
            result[4],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        whenever(
            updateFluxo(
                sizeAll = 22f,
                count = 3f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = percentage(7f, 22f)
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
        assertEquals(result.count(), 8)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        assertEquals(
            result[6],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_fluxo",
                currentProgress = percentage(7f, 22f)
            )
        )
        assertEquals(
            result[7],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        whenever(
            updateLocal(
                sizeAll = 22f,
                count = 4f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = percentage(10f, 22f)
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
        assertEquals(result.count(), 11)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        assertEquals(
            result[9],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = percentage(10f, 22f)
            )
        )
        assertEquals(
            result[10],
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
    fun `Check return failure usecase if have error in usecase UpdateRLocalFluxo`() = runTest {
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        whenever(
            updateRLocalFluxo(
                sizeAll = 22f,
                count = 5f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = percentage(13f, 22f)
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
        assertEquals(result.count(), 14)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        assertEquals(
            result[12],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_r_local_fluxo",
                currentProgress = percentage(13f, 22f)
            )
        )
        assertEquals(
            result[13],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessRLocalFluxo()
        whenever(
            updateTerceiro(
                sizeAll = 22f,
                count = 6f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(16f, 22f)
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
        assertEquals(result.count(), 17)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateRLocalFluxo(result)
        assertEquals(
            result[15],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(16f, 22f)
            )
        )
        assertEquals(
            result[16],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
        wheneverSuccessRLocalFluxo()
        wheneverSuccessTerceiro()
        whenever(
            updateVisitante(
                sizeAll = 22f,
                count = 7f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(19f, 22f)
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
        assertEquals(result.count(), 20)
        checkResultUpdateColab(result)
        checkResultUpdateEquip(result)
        checkResultUpdateFluxo(result)
        checkResultUpdateLocal(result)
        checkResultUpdateRLocalFluxo(result)
        checkResultUpdateTerceiro(result)
        assertEquals(
            result[18],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(19f, 22f)
            )
        )
        assertEquals(
            result[19],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
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
        assertEquals(result.count(), 22)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(1f, 22f)
            )
        )
        checkResultUpdateFluxo(result)
        assertEquals(
            result[18],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(19f, 22f)
            )
        )
        assertEquals(
            result[21],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
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
        assertEquals(result.count(), 22)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(1f, 22f)
            )
        )
        checkResultUpdateFluxo(result)
        assertEquals(
            result[18],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(19f, 22f)
            )
        )
        assertEquals(
            result[21],
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
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessFluxo()
        wheneverSuccessLocal()
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

    private fun wheneverSuccessColab() = runTest {
        whenever(
            updateColab(
                sizeAll = 22f,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(2f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(3f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateColab(result: List<ConfigState>) = runTest {
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = percentage(1f, 22f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = percentage(2f, 22f)
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = percentage(3f, 22f)
            )
        )
    }

    private fun wheneverSuccessEquip() = runTest {
        whenever(
            updateEquip(
                sizeAll = 22f,
                count = 2f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_equip",
                    currentProgress = percentage(4f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                    currentProgress = percentage(5f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_equip",
                    currentProgress = percentage(6f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateEquip(result: List<ConfigState>) = runTest {
        assertEquals(
            result[3],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = percentage(4f, 22f)
            )
        )
        assertEquals(
            result[4],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = percentage(5f, 22f)
            )
        )
        assertEquals(
            result[5],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_equip",
                currentProgress = percentage(6f, 22f)
            )
        )
    }

    private fun wheneverSuccessFluxo() = runTest {
        whenever(
            updateFluxo(
                sizeAll = 22f,
                count = 3f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_fluxo",
                    currentProgress = percentage(7f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                    currentProgress = percentage(8f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_fluxo",
                    currentProgress = percentage(9f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateFluxo(result: List<ConfigState>) = runTest {
        assertEquals(
            result[6],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_fluxo",
                currentProgress = percentage(7f, 22f)
            )
        )
        assertEquals(
            result[7],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_fluxo do Web Service",
                currentProgress = percentage(8f, 22f)
            )
        )
        assertEquals(
            result[8],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_fluxo",
                currentProgress = percentage(9f, 22f)
            )
        )
    }

    private fun wheneverSuccessLocal() = runTest {
        whenever(
            updateLocal(
                sizeAll = 22f,
                count = 4f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local",
                    currentProgress = percentage(10f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                    currentProgress = percentage(11f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local",
                    currentProgress = percentage(12f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateLocal(result: List<ConfigState>) = runTest {
        assertEquals(
            result[9],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = percentage(10f, 22f)
            )
        )
        assertEquals(
            result[10],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = percentage(11f, 22f)
            )
        )
        assertEquals(
            result[11],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local",
                currentProgress = percentage(12f, 22f)
            )
        )
    }

    private fun wheneverSuccessRLocalFluxo() = runTest {
        whenever(
            updateRLocalFluxo(
                sizeAll = 22f,
                count = 5f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_r_local_fluxo",
                    currentProgress = percentage(13f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                    currentProgress = percentage(14f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                    currentProgress = percentage(15f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateRLocalFluxo(result: List<ConfigState>) = runTest {
        assertEquals(
            result[12],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_r_local_fluxo",
                currentProgress = percentage(13f, 22f)
            )
        )
        assertEquals(
            result[13],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_r_local_fluxo do Web Service",
                currentProgress = percentage(14f, 22f)
            )
        )
        assertEquals(
            result[14],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_r_local_fluxo",
                currentProgress = percentage(15f, 22f)
            )
        )
    }

    private fun wheneverSuccessTerceiro() = runTest {
        whenever(
            updateTerceiro(
                sizeAll = 22f,
                count = 6f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(16f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(17f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(18f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateTerceiro(result: List<ConfigState>) = runTest {
        assertEquals(
            result[15],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(16f, 22f)
            )
        )
        assertEquals(
            result[16],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(17f, 22f)
            )
        )
        assertEquals(
            result[17],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(18f, 22f)
            )
        )
    }

    private fun wheneverSuccessVisitante() = runTest {
        whenever(
            updateVisitante(
                sizeAll = 22f,
                count = 7f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(19f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = percentage(20f, 22f)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = percentage(21f, 22f)
                ),
            )
        )
    }

    private fun checkResultUpdateVisitante(result: List<ConfigState>) = runTest {
        assertEquals(
            result[18],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(19f, 22f)
            )
        )
        assertEquals(
            result[19],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = percentage(20f, 22f)
            )
        )
        assertEquals(
            result[20],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = percentage(21f, 22f)
            )
        )
    }

}
