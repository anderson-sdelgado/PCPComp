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
                failure = "Error SendDataConfig -> Failure Usecase -> java.lang.NumberFormatException: For input string: \"1df52\"",
                msgProgress = "Error SendDataConfig -> Failure Usecase -> java.lang.NumberFormatException: For input string: \"1df52\"",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Error SendDataConfig -> Failure Usecase -> java.lang.NumberFormatException: For input string: \"1df52\""
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
                DatasourceException(cause = NullPointerException())
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
                failure = "Error SendDataConfig -> Failure Datasource -> java.lang.NullPointerException",
                msgProgress = "Error SendDataConfig -> Failure Datasource -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Error SendDataConfig -> Failure Datasource -> java.lang.NullPointerException"
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
                UsecaseException(cause = NullPointerException())
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
                failure = "Error SaveDataConfig -> Failure Usecase -> java.lang.NullPointerException",
                msgProgress = "Error SaveDataConfig -> Failure Usecase -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Error SaveDataConfig -> Failure Usecase -> java.lang.NullPointerException"
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
                UsecaseException(cause = NullPointerException())
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
                currentProgress = porc(1f, 16f)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error CleanColab -> Failure Usecase -> java.lang.NullPointerException",
                msgProgress = "Error CleanColab -> Failure Usecase -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Error CleanColab -> Failure Usecase -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `check return failure datasource if have error in usecase CleanColab is datasource`() =
        runTest {
            whenever(
                cleanColab()
            ).thenReturn(
                Result.failure(
                    DatasourceException(cause = NullPointerException())
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
                    currentProgress = porc(1f, sizeAll)
                )
            )
            assertEquals(
                result[1],
                ConfigState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    failure = "Error CleanColab -> Failure Datasource -> java.lang.NullPointerException",
                    msgProgress = "Error CleanColab -> Failure Datasource -> java.lang.NullPointerException",
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
                UsecaseException(cause = NullPointerException())
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
                currentProgress = porc(1f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f , sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error RecoverColabServer -> Failure Usecase -> java.lang.NullPointerException",
                msgProgress = "Error RecoverColabServer -> Failure Usecase -> java.lang.NullPointerException",
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
                DatasourceException(cause = Exception())
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
                currentProgress = porc(1f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f ,sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = porc(3f, sizeAll),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error SaveAllColab -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error SaveAllColab -> Failure Datasource -> java.lang.Exception",
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
                UsecaseException(cause = NullPointerException())
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
        assertEquals(result.count(), 5)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f , sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = porc(3f, sizeAll),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeAll)
            )
        )
        assertEquals(
            result[4],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error CleanEquip -> Failure Usecase -> java.lang.NullPointerException",
                msgProgress = "Error CleanEquip -> Failure Usecase -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.saveTokenAndUpdateAllDatabase()
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Error CleanEquip -> Failure Usecase -> java.lang.NullPointerException"
        )
    }

    /////////////////////// EQUIP /////////////////////////

    @Test
    fun `check return failure datasource if have error in datasource CleanEquip`() = runTest {
        whenever(
            cleanEquip()
        ).thenReturn(
            Result.failure(
                DatasourceException(cause = NullPointerException())
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
        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error CleanEquip -> Failure Datasource -> java.lang.NullPointerException",
                msgProgress = "Error CleanEquip -> Failure Datasource -> java.lang.NullPointerException",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = porc(5f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error RecoverEquipServer -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error RecoverEquipServer -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllEquip(count = 2f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_equip",
                currentProgress = porc(4f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
                currentProgress = porc(5f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_equip",
                currentProgress = porc(6f, sizeAll),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error SaveAllEquip -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error SaveAllEquip -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = NullPointerException())
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
        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = porc(7f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error CleanLocal -> Failure Datasource -> java.lang.NullPointerException",
                msgProgress = "Error CleanLocal -> Failure Datasource -> java.lang.NullPointerException",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = porc(7f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = porc(8f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error RecoverLocalServer -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error RecoverLocalServer -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllLocal(count = 3f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_local",
                currentProgress = porc(7f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_local do Web Service",
                currentProgress = porc(8f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_local",
                currentProgress = porc(9f, sizeAll),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error SaveAllLocal -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error SaveAllLocal -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = NullPointerException())
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
        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(10f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error CleanTerceiro -> Failure Datasource -> java.lang.NullPointerException",
                msgProgress = "Error CleanTerceiro -> Failure Datasource -> java.lang.NullPointerException",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(10f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(11f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error RecoverTerceiroServer -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error RecoverTerceiroServer -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllTerceiro(count = 4f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(10f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(11f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(12f, sizeAll),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error SaveAllTerceiro -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error SaveAllTerceiro -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = NullPointerException())
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
        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error CleanVisitante -> Failure Datasource -> java.lang.NullPointerException",
                msgProgress = "Error CleanVisitante -> Failure Datasource -> java.lang.NullPointerException",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(14f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error RecoverVisitanteServer -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error RecoverVisitanteServer -> Failure Datasource -> java.lang.Exception",
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
                DatasourceException(cause = Exception())
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
        val result = viewModel.updateAllVisitante(count = 5f, sizeAll = sizeAll).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeAll)
            )
        )
        assertEquals(
            result[1],
            ConfigState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(14f, sizeAll),
            )
        )
        assertEquals(
            result[2],
            ConfigState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = porc(15f, sizeAll),
            )
        )
        assertEquals(
            result[3],
            ConfigState(
                errors = Errors.UPDATE,
                flagDialog = true,
                failure = "Error SaveAllVisitante -> Failure Datasource -> java.lang.Exception",
                msgProgress = "Error SaveAllVisitante -> Failure Datasource -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource DetCheckUpdateAllTable`() = runTest {
        wheneverSuccessColab()
        wheneverSuccessEquip()
        wheneverSuccessLocal()
        wheneverSuccessTerceiro()
        wheneverSuccessVisitante()
        whenever(setCheckUpdateAllTable(FlagUpdate.UPDATED)).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
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
                currentProgress = porc(1f, sizeAll)
            )
        )
        assertEquals(
            result[12],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeAll)
            )
        )
        assertEquals(
            result[15],
            ConfigState(
                errors = Errors.EXCEPTION,
                flagDialog = true,
                failure = "Error SetCheckUpdateAllTable -> Failure Datasource -> java.lang.Exception",
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
                currentProgress = porc(1f, sizeAll)
            )
        )
        assertEquals(
            result[12],
            ConfigState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(13f, sizeAll)
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

val sizeAll = 16f