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
import br.com.usinasantafe.pcpcomp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
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
    private val cleanColab = mock<CleanColab>()
    private val cleanEquip = mock<CleanEquip>()
    private val cleanFluxo = mock<CleanFluxo>()
    private val cleanLocal = mock<CleanLocal>()
    private val cleanRLocalFluxo = mock<CleanRLocalFluxo>()
    private val cleanTerceiro = mock<CleanTerceiro>()
    private val cleanVisitante = mock<CleanVisitante>()
    private val getAllColabServer = mock<GetAllColabServer>()
    private val getAllEquipServer = mock<GetAllEquipServer>()
    private val getAllFluxoServer = mock<GetAllFluxoServer>()
    private val getAllLocalServer = mock<GetAllLocalServer>()
    private val getAllRLocalFluxoServer = mock<GetAllRLocalFluxoServer>()
    private val getAllTerceiroServer = mock<GetAllTerceiroServer>()
    private val getAllVisitanteServer = mock<GetAllVisitanteServer>()
    private val saveAllColab = mock<SaveAllColab>()
    private val saveAllEquip = mock<SaveAllEquip>()
    private val saveAllFluxo = mock<SaveAllFluxo>()
    private val saveAllLocal = mock<SaveAllLocal>()
    private val saveAllRLocalFluxo = mock<SaveAllRLocalFluxo>()
    private val saveAllTerceiro = mock<SaveAllTerceiro>()
    private val saveAllVisitante = mock<SaveAllVisitante>()


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
    fun `check return null if don't have Config table internal`() = runTest {
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
    fun `check return data if have Config table internal`() = runTest {
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
    fun `check return msg when field empty`() = runTest {
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
    fun `check return failure datasource if have errors in Datasource`() = runTest {
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
    fun `check return failure usecase in save if have errors in Save data Usecase`() = runTest {
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
    fun `check return finally sent and save Config`() = runTest {
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
    fun `check return failure usecase if have error in usecase CleanColab`() = runTest {
        whenever(
            updateColab(
                sizeAll = 22f,
                count = 1f
            )
        ).thenReturn(
            listOf(
                ResultUpdate(
                    flagFailure = true,
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 22f)
                )
            ).asFlow()
        )
        val viewModel = getViewModel()
//        val result = viewModel.updateAllDatabase().test {
//            assertEquals(
//                awaitItem(),
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_colab",
//                    currentProgress = percentage(1f, 16f)
//                )
//            )
//        }

//        assertEquals(result.count(), 2)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_colab",
//                currentProgress = percentage(1f, 16f)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
//                msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//        viewModel.saveTokenAndUpdateAllDatabase()
//        assertEquals(
//            viewModel.uiState.value.msgProgress,
//            "Failure Usecase -> CleanColab -> java.lang.NullPointerException"
//        )
    }
//
//    @Test
//    fun `check return failure datasource if have error in usecase CleanColab is datasource`() =
//        runTest {
//            whenever(
//                cleanColab()
//            ).thenReturn(
//                Result.failure(
//                    DatasourceException(
//                        function = "CleanColab",
//                        cause = NullPointerException()
//                    )
//                )
//            )
//            val viewModel = getViewModel()
//            viewModel.onNumberChanged("16997417840")
//            viewModel.onPasswordChanged("12345")
//            val result = viewModel.updateAllDatabase().toList()
//            assertEquals(result.count(), 2)
//            assertEquals(
//                result[0],
//                ConfigState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_colab",
//                    currentProgress = percentage(1f, sizeUpdateConfig)
//                )
//            )
//            assertEquals(
//                result[1],
//                ConfigState(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "Failure Datasource -> CleanColab -> java.lang.NullPointerException",
//                    msgProgress = "Failure Datasource -> CleanColab -> java.lang.NullPointerException",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `check return failure usecase if have error in usecase RecoverColabServer`() = runTest {
//        whenever(
//            cleanColab()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllColabServer()
//        ).thenReturn(
//            Result.failure(
//                UsecaseException(
//                    function = "RecoverColabServer",
//                    cause = NullPointerException()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        val result = viewModel.updateAllDatabase().toList()
//        assertEquals(result.count(), 3)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_colab",
//                currentProgress = percentage(1f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
//                currentProgress = percentage(2f , sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException",
//                msgProgress = "Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource SaveAllColab`() = runTest {
//        whenever(
//            cleanColab()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllColabServer()
//        ).thenReturn(
//            Result.success(colabList)
//        )
//        whenever(
//            saveAllColab(colabList)
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SaveAllColab",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        val result = viewModel.updateAllDatabase().toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_colab",
//                currentProgress = percentage(1f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
//                currentProgress = percentage(2f , sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_colab",
//                currentProgress = percentage(3f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[3],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SaveAllColab -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> SaveAllColab -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure usecase if have error in usecase CleanEquip`() = runTest {
//        wheneverSuccessToken()
//        wheneverSuccessColab()
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.failure(
//                UsecaseException(
//                    function = "CleanEquip",
//                    cause = NullPointerException()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        viewModel.updateVersion("6.00")
//        val result = viewModel.updateAllDatabase().toList()
//        assertEquals(result.count(), 5)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_colab",
//                currentProgress = percentage(1f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
//                currentProgress = percentage(2f , sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_colab",
//                currentProgress = percentage(3f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[3],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = percentage(4f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[4],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
//                msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//        viewModel.saveTokenAndUpdateAllDatabase()
//        assertEquals(
//            viewModel.uiState.value.msgProgress,
//            "Failure Usecase -> CleanEquip -> java.lang.NullPointerException"
//        )
//    }

    /////////////////////// EQUIP /////////////////////////
//
//    @Test
//    fun `check return failure datasource if have error in datasource CleanEquip`() = runTest {
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "CleanEquip",
//                    cause = NullPointerException()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 2)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = percentage(4f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> CleanEquip -> java.lang.NullPointerException",
//                msgProgress = "Failure Datasource -> CleanEquip -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource RecoverAllEquip`() = runTest {
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllEquipServer()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "RecoverEquipServer",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 3)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = percentage(4f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
//                currentProgress = percentage(5f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> RecoverEquipServer -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> RecoverEquipServer -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource SaveAllEquip`() = runTest {
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllEquipServer()
//        ).thenReturn(
//            Result.success(equipList)
//        )
//        whenever(
//            saveAllEquip(equipList)
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SaveAllEquip",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = percentage(4f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
//                currentProgress = percentage(5f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_equip",
//                currentProgress = percentage(6f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[3],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SaveAllEquip -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> SaveAllEquip -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    /////////////////////// LOCAL /////////////////////////
//
//    @Test
//    fun `check return failure datasource if have error in datasource CleanLocal`() = runTest {
//        whenever(
//            cleanLocal()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "CleanLocal",
//                    cause = NullPointerException()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 2)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_local",
//                currentProgress = percentage(7f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> CleanLocal -> java.lang.NullPointerException",
//                msgProgress = "Failure Datasource -> CleanLocal -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource RecoverAllLocal`() = runTest {
//        whenever(
//            cleanLocal()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllLocalServer()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "RecoverLocalServer",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 3)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_local",
//                currentProgress = percentage(7f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
//                currentProgress = percentage(8f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> RecoverLocalServer -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> RecoverLocalServer -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource SaveAllLocal`() = runTest {
//        whenever(
//            cleanLocal()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllLocalServer()
//        ).thenReturn(
//            Result.success(localList)
//        )
//        whenever(
//            saveAllLocal(localList)
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SaveAllLocal",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_local",
//                currentProgress = percentage(7f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
//                currentProgress = percentage(8f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_local",
//                currentProgress = percentage(9f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[3],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SaveAllLocal -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> SaveAllLocal -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    /////////////////////// TERCEIRO /////////////////////////
//
//    @Test
//    fun `check return failure datasource if have error in datasource CleanTerceiro`() = runTest {
//        whenever(
//            cleanTerceiro()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "CleanTerceiro",
//                    cause = NullPointerException()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 2)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_terceiro",
//                currentProgress = percentage(10f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> CleanTerceiro -> java.lang.NullPointerException",
//                msgProgress = "Failure Datasource -> CleanTerceiro -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource RecoverAllTerceiro`() = runTest {
//        whenever(
//            cleanTerceiro()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllTerceiroServer()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "RecoverTerceiroServer",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 3)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_terceiro",
//                currentProgress = percentage(10f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
//                currentProgress = percentage(11f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> RecoverTerceiroServer -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> RecoverTerceiroServer -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource SaveAllTerceiro`() = runTest {
//        whenever(
//            cleanTerceiro()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllTerceiroServer()
//        ).thenReturn(
//            Result.success(terceiroList)
//        )
//        whenever(
//            saveAllTerceiro(terceiroList)
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SaveAllTerceiro",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_terceiro",
//                currentProgress = percentage(10f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
//                currentProgress = percentage(11f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_terceiro",
//                currentProgress = percentage(12f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[3],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SaveAllTerceiro -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> SaveAllTerceiro -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    /////////////////////// VISITANTE /////////////////////////
//
//    @Test
//    fun `check return failure datasource if have error in datasource CleanVisitante`() = runTest {
//        whenever(
//            cleanVisitante()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "CleanVisitante",
//                    cause = NullPointerException()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 2)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_visitante",
//                currentProgress = percentage(13f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> CleanVisitante -> java.lang.NullPointerException",
//                msgProgress = "Failure Datasource -> CleanVisitante -> java.lang.NullPointerException",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource RecoverAllVisitante`() = runTest {
//        whenever(
//            cleanVisitante()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllVisitanteServer()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "RecoverVisitanteServer",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 3)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_visitante",
//                currentProgress = percentage(13f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
//                currentProgress = percentage(14f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> RecoverVisitanteServer -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> RecoverVisitanteServer -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource SaveAllVisitante`() = runTest {
//        whenever(
//            cleanVisitante()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllVisitanteServer()
//        ).thenReturn(
//            Result.success(visitanteList)
//        )
//        whenever(
//            saveAllVisitante(visitanteList)
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SaveAllVisitante",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeUpdateConfig).toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_visitante",
//                currentProgress = percentage(13f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[1],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
//                currentProgress = percentage(14f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[2],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_visitante",
//                currentProgress = percentage(15f, sizeUpdateConfig),
//            )
//        )
//        assertEquals(
//            result[3],
//            ConfigState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SaveAllVisitante -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> SaveAllVisitante -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return failure datasource if have error in datasource SetCheckUpdateAllTable`() = runTest {
//        wheneverSuccessColab()
//        wheneverSuccessEquip()
//        wheneverSuccessLocal()
//        wheneverSuccessTerceiro()
//        wheneverSuccessVisitante()
//        whenever(setCheckUpdateAllTable(FlagUpdate.UPDATED)).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SetCheckUpdateAllTable",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllDatabase().toList()
//        assertEquals(result.count(), 16)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_colab",
//                currentProgress = percentage(1f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[12],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_visitante",
//                currentProgress = percentage(13f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[15],
//            ConfigState(
//                errors = Errors.EXCEPTION,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SetCheckUpdateAllTable -> java.lang.Exception",
//            )
//        )
//    }
//
//    @Test
//    fun `check return success if all update run correctly`() = runTest {
//        wheneverSuccessColab()
//        wheneverSuccessEquip()
//        wheneverSuccessLocal()
//        wheneverSuccessTerceiro()
//        wheneverSuccessVisitante()
//        whenever(setCheckUpdateAllTable(FlagUpdate.UPDATED)).thenReturn(
//            Result.success(true)
//        )
//        val viewModel = getViewModel()
//        val result = viewModel.updateAllDatabase().toList()
//        assertEquals(result.count(), 16)
//        assertEquals(
//            result[0],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_colab",
//                currentProgress = percentage(1f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[12],
//            ConfigState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_visitante",
//                currentProgress = percentage(13f, sizeUpdateConfig)
//            )
//        )
//        assertEquals(
//            result[15],
//            ConfigState(
//                flagDialog = true,
//                flagProgress = true,
//                msgProgress = "Atualização de dados realizado com sucesso!",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return success if saveTokenAndUpdateAllDatabase is success`() = runTest {
//        wheneverSuccessToken()
//        wheneverSuccessColab()
//        wheneverSuccessEquip()
//        wheneverSuccessLocal()
//        wheneverSuccessTerceiro()
//        wheneverSuccessVisitante()
//        val viewModel = getViewModel()
//        viewModel.onNumberChanged("16997417840")
//        viewModel.onPasswordChanged("12345")
//        viewModel.updateVersion("6.00")
//        viewModel.saveTokenAndUpdateAllDatabase()
//        assertEquals(viewModel.uiState.value.msgProgress, "Atualização de dados realizado com sucesso!")
//    }

    private fun wheneverSuccessToken() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(Result.success(1))
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        ).thenReturn(Result.success(true))
    }

    private fun wheneverSuccessColab() = runTest {
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getAllColabServer()
        ).thenReturn(
            Result.success(colabList)
        )
        whenever(
            saveAllColab(colabList)
        ).thenReturn(
            Result.success(true)
        )
    }

    private fun wheneverSuccessEquip() = runTest {
        whenever(
            cleanEquip()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getAllEquipServer()
        ).thenReturn(
            Result.success(equipList)
        )
        whenever(
            saveAllEquip(equipList)
        ).thenReturn(
            Result.success(true)
        )
    }

    private fun wheneverSuccessLocal() = runTest {
        whenever(
            cleanLocal()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getAllLocalServer()
        ).thenReturn(
            Result.success(localList)
        )
        whenever(
            saveAllLocal(localList)
        ).thenReturn(
            Result.success(true)
        )
    }

    private fun wheneverSuccessTerceiro() = runTest {
        whenever(
            cleanTerceiro()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getAllTerceiroServer()
        ).thenReturn(
            Result.success(terceiroList)
        )
        whenever(
            saveAllTerceiro(terceiroList)
        ).thenReturn(
            Result.success(true)
        )
    }

    private fun wheneverSuccessVisitante() = runTest {
        whenever(
            cleanVisitante()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getAllVisitanteServer()
        ).thenReturn(
            Result.success(visitanteList)
        )
        whenever(
            saveAllVisitante(visitanteList)
        ).thenReturn(
            Result.success(true)
        )
    }

}

val sizeUpdateConfig = 16f