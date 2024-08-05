package br.com.usinasantafe.pcpcomp.presenter.config

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.usecases.config.RecoverConfigInternal
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SaveDataConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SendDataConfig
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetCheckUpdateAllTable
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverEquipServer
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverLocalServer
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverTerceiroServer
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverVisitanteServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val recoverConfigInternal = mock<RecoverConfigInternal>()
    private val sendDataConfig = mock<SendDataConfig>()
    private val saveDataConfig = mock<SaveDataConfig>()
    private val cleanColab = mock<CleanColab>()
    private val cleanEquip = mock<CleanEquip>()
    private val cleanLocal = mock<CleanLocal>()
    private val cleanTerceiro = mock<CleanTerceiro>()
    private val cleanVisitante = mock<CleanVisitante>()
    private val recoverColabServer = mock<RecoverColabServer>()
    private val recoverEquipServer = mock<RecoverEquipServer>()
    private val recoverLocalServer = mock<RecoverLocalServer>()
    private val recoverTerceiroServer = mock<RecoverTerceiroServer>()
    private val recoverVisitanteServer = mock<RecoverVisitanteServer>()
    private val saveAllColab = mock<SaveAllColab>()
    private val saveAllEquip = mock<SaveAllEquip>()
    private val saveAllLocal = mock<SaveAllLocal>()
    private val saveAllTerceiro = mock<SaveAllTerceiro>()
    private val saveAllVisitante = mock<SaveAllVisitante>()
    private val setCheckUpdateAllTable = mock<SetCheckUpdateAllTable>()

    @Test
    fun `check return null if don't have Config table internal`() = runTest {
        whenever(recoverConfigInternal()).thenReturn(null)
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,

        )
        viewModel.returnDataConfig()
        assertEquals(viewModel.uiState.value.number, "")
        assertEquals(viewModel.uiState.value.password, "")
    }

    @Test
    fun `check return data if have Config table internal`() = runTest {
        val configModel = ConfigModel(
            number = "16997417840",
            password = "12345"
        )
        whenever(recoverConfigInternal()).thenReturn(Result.success(configModel))
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.returnDataConfig()
        assertEquals(viewModel.uiState.value.number, "16997417840")
        assertEquals(viewModel.uiState.value.password, "12345")
    }

    @Test
    fun `check return msg when field empty`() = runTest {
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.FIELDEMPTY)
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
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16dfda")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = porc(1f, 3f)
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
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = porc(1f, 3f)
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
            Result.success(1L)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1L
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveDataConfig",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = porc(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = porc(2f, 3f),
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
            Result.success(1L)
        )
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1L
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.token().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Enviando dados de Token",
                currentProgress = porc(1f, 3f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados de Token",
                currentProgress = porc(2f, 3f),
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
        wheneverSuccessToken()
        whenever(
            cleanColab()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanColab",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, 16f)
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
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanColab -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `check return failure datasource if have error in usecase CleanColab is datasource`() =
        runTest {
            whenever(
                cleanColab()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "CleanColab",
                        cause = NullPointerException()
                    )
                )
            )
            val viewModel = ConfigViewModel(
                recoverConfigInternal = recoverConfigInternal,
                sendDataConfig = sendDataConfig,
                saveDataConfig = saveDataConfig,
                cleanColab = cleanColab,
                cleanEquip = cleanEquip,
                cleanLocal = cleanLocal,
                cleanTerceiro = cleanTerceiro,
                cleanVisitante = cleanVisitante,
                recoverColabServer = recoverColabServer,
                recoverEquipServer = recoverEquipServer,
                recoverLocalServer = recoverLocalServer,
                recoverTerceiroServer = recoverTerceiroServer,
                recoverVisitanteServer = recoverVisitanteServer,
                saveAllColab = saveAllColab,
                saveAllEquip = saveAllEquip,
                saveAllLocal = saveAllLocal,
                saveAllTerceiro = saveAllTerceiro,
                saveAllVisitante = saveAllVisitante,
                setCheckUpdateAllTable = setCheckUpdateAllTable,
            )
            viewModel.updateNumber("16997417840")
            viewModel.updatePassword("12345")
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                ConfigState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = porc(1f, sizeUpdateConfig)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Datasource -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Datasource -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `check return failure usecase if have error in usecase RecoverColabServer`() = runTest {
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverColabServer",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f , sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SaveAllColab`() = runTest {
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.success(colabList)
        )
        whenever(
            saveAllColab(colabList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SaveAllColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f ,sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = porc(3f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SaveAllColab -> java.lang.Exception",
                msgProgress = "Failure Datasource -> SaveAllColab -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure usecase if have error in usecase CleanEquip`() = runTest {
        wheneverSuccessToken()
        wheneverSuccessColab()
        whenever(
            cleanEquip()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanEquip",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 5)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f , sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = porc(3f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeUpdateConfig)
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
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanEquip -> java.lang.NullPointerException"
        )
    }

    /////////////////////// EQUIP /////////////////////////

    @Test
    fun `check return failure datasource if have error in datasource CleanEquip`() = runTest {
        whenever(
            cleanEquip()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "CleanEquip",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> CleanEquip -> java.lang.NullPointerException",
                msgProgress = "Failure Datasource -> CleanEquip -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource RecoverAllEquip`() = runTest {
        whenever(
            cleanEquip()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverEquipServer()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "RecoverEquipServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = porc(5f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> RecoverEquipServer -> java.lang.Exception",
                msgProgress = "Failure Datasource -> RecoverEquipServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SaveAllEquip`() = runTest {
        whenever(
            cleanEquip()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverEquipServer()
        ).thenReturn(
            Result.success(equipList)
        )
        whenever(
            saveAllEquip(equipList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SaveAllEquip",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = porc(5f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_equip",
                currentProgress = porc(6f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SaveAllEquip -> java.lang.Exception",
                msgProgress = "Failure Datasource -> SaveAllEquip -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    /////////////////////// LOCAL /////////////////////////

    @Test
    fun `check return failure datasource if have error in datasource CleanLocal`() = runTest {
        whenever(
            cleanLocal()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "CleanLocal",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = porc(7f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> CleanLocal -> java.lang.NullPointerException",
                msgProgress = "Failure Datasource -> CleanLocal -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource RecoverAllLocal`() = runTest {
        whenever(
            cleanLocal()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverLocalServer()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "RecoverLocalServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = porc(7f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = porc(8f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> RecoverLocalServer -> java.lang.Exception",
                msgProgress = "Failure Datasource -> RecoverLocalServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SaveAllLocal`() = runTest {
        whenever(
            cleanLocal()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverLocalServer()
        ).thenReturn(
            Result.success(localList)
        )
        whenever(
            saveAllLocal(localList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SaveAllLocal",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = porc(7f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = porc(8f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local",
                currentProgress = porc(9f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SaveAllLocal -> java.lang.Exception",
                msgProgress = "Failure Datasource -> SaveAllLocal -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    /////////////////////// TERCEIRO /////////////////////////

    @Test
    fun `check return failure datasource if have error in datasource CleanTerceiro`() = runTest {
        whenever(
            cleanTerceiro()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "CleanTerceiro",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(10f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> CleanTerceiro -> java.lang.NullPointerException",
                msgProgress = "Failure Datasource -> CleanTerceiro -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource RecoverAllTerceiro`() = runTest {
        whenever(
            cleanTerceiro()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverTerceiroServer()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "RecoverTerceiroServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(10f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(11f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> RecoverTerceiroServer -> java.lang.Exception",
                msgProgress = "Failure Datasource -> RecoverTerceiroServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SaveAllTerceiro`() = runTest {
        whenever(
            cleanTerceiro()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverTerceiroServer()
        ).thenReturn(
            Result.success(terceiroList)
        )
        whenever(
            saveAllTerceiro(terceiroList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SaveAllTerceiro",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(10f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(11f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(12f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SaveAllTerceiro -> java.lang.Exception",
                msgProgress = "Failure Datasource -> SaveAllTerceiro -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    /////////////////////// VISITANTE /////////////////////////

    @Test
    fun `check return failure datasource if have error in datasource CleanVisitante`() = runTest {
        whenever(
            cleanVisitante()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "CleanVisitante",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> CleanVisitante -> java.lang.NullPointerException",
                msgProgress = "Failure Datasource -> CleanVisitante -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource RecoverAllVisitante`() = runTest {
        whenever(
            cleanVisitante()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverVisitanteServer()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "RecoverVisitanteServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(14f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> RecoverVisitanteServer -> java.lang.Exception",
                msgProgress = "Failure Datasource -> RecoverVisitanteServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SaveAllVisitante`() = runTest {
        whenever(
            cleanVisitante()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverVisitanteServer()
        ).thenReturn(
            Result.success(visitanteList)
        )
        whenever(
            saveAllVisitante(visitanteList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SaveAllVisitante",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeUpdateConfig).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(14f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = porc(15f, sizeUpdateConfig),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SaveAllVisitante -> java.lang.Exception",
                msgProgress = "Failure Datasource -> SaveAllVisitante -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SetCheckUpdateAllTable`() = runTest {
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessLocal()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(setCheckUpdateAllTable(FlagUpdate.UPDATED)).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SetCheckUpdateAllTable",
                    cause = Exception()
                )
            )
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 16)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[12],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[15],
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
        wheneverSuccessLocal()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(setCheckUpdateAllTable(FlagUpdate.UPDATED)).thenReturn(
            Result.success(true)
        )
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 16)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[12],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeUpdateConfig)
            )
        )
        assertEquals(
            result[15],
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
        wheneverSuccessLocal()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        val viewModel = ConfigViewModel(
            recoverConfigInternal = recoverConfigInternal,
            sendDataConfig = sendDataConfig,
            saveDataConfig = saveDataConfig,
            cleanColab = cleanColab,
            cleanEquip = cleanEquip,
            cleanLocal = cleanLocal,
            cleanTerceiro = cleanTerceiro,
            cleanVisitante = cleanVisitante,
            recoverColabServer = recoverColabServer,
            recoverEquipServer = recoverEquipServer,
            recoverLocalServer = recoverLocalServer,
            recoverTerceiroServer = recoverTerceiroServer,
            recoverVisitanteServer = recoverVisitanteServer,
            saveAllColab = saveAllColab,
            saveAllEquip = saveAllEquip,
            saveAllLocal = saveAllLocal,
            saveAllTerceiro = saveAllTerceiro,
            saveAllVisitante = saveAllVisitante,
            setCheckUpdateAllTable = setCheckUpdateAllTable,
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.updateVersion("6.00")
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(viewModel.uiState.value.msgProgress, "Atualização de dados realizado com sucesso!")
    }

    private fun wheneverSuccessToken() = runTest {
        whenever(
            sendDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00"
            )
        ).thenReturn(Result.success(1L))
        whenever(
            saveDataConfig(
                number = "16997417840",
                password = "12345",
                version = "6.00",
                idBD = 1L
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
            recoverColabServer()
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
            recoverEquipServer()
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
            recoverLocalServer()
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
            recoverTerceiroServer()
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
            recoverVisitanteServer()
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

val colabList = listOf(
    Colab(
        matricColab = 19759,
        nomeColab = "ANDERSON DA SILVA DELGADO"
    )
)

val equipList = listOf(
    Equip(
        idEquip = 1,
        nroEquip = 10
    )
)

val localList = listOf(
    Local(
        idLocal = 1,
        descrLocal = "USINA"
    )
)

val terceiroList = listOf(
    Terceiro(
        idTerceiro = 1,
        idBDTerceiro = 1,
        cpfTerceiro = "123.456.789-00",
        nomeTerceiro = "Terceiro",
        empresaTerceiro = "Empresa Terceiro"
    )
)


val visitanteList = listOf(
    Visitante(
        idVisitante = 1,
        cpfVisitante = "123.456.789-00",
        nomeVisitante = "Visitante",
        empresaVisitante = "Empresa Visitante"
    )
)

val sizeUpdateConfig = 16f