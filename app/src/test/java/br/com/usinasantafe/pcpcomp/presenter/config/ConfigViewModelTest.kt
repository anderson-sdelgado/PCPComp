package br.com.usinasantafe.pcpcomp.presenter.config

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Test
    fun `check return null if don't have Config table internal`() = runTest {
        val recoverConfigInternal = mock<RecoverConfigInternal>()
        val sendDataConfig = mock<SendDataConfig>()
        val saveDataConfig = mock<SaveDataConfig>()
        val cleanColab = mock<CleanColab>()
        val cleanEquip = mock<CleanEquip>()
        val cleanLocal = mock<CleanLocal>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val recoverColabServer = mock<RecoverColabServer>()
        val recoverEquipServer = mock<RecoverEquipServer>()
        val recoverLocalServer = mock<RecoverLocalServer>()
        val recoverTerceiroServer = mock<RecoverTerceiroServer>()
        val recoverVisitanteServer = mock<RecoverVisitanteServer>()
        val saveAllColab = mock<SaveAllColab>()
        val saveAllEquip = mock<SaveAllEquip>()
        val saveAllLocal = mock<SaveAllLocal>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
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
            saveAllVisitante = saveAllVisitante
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
        val recoverConfigInternal = mock<RecoverConfigInternal>()
        val sendDataConfig = mock<SendDataConfig>()
        val saveDataConfig = mock<SaveDataConfig>()
        val cleanColab = mock<CleanColab>()
        val cleanEquip = mock<CleanEquip>()
        val cleanLocal = mock<CleanLocal>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val recoverColabServer = mock<RecoverColabServer>()
        val recoverEquipServer = mock<RecoverEquipServer>()
        val recoverLocalServer = mock<RecoverLocalServer>()
        val recoverTerceiroServer = mock<RecoverTerceiroServer>()
        val recoverVisitanteServer = mock<RecoverVisitanteServer>()
        val saveAllColab = mock<SaveAllColab>()
        val saveAllEquip = mock<SaveAllEquip>()
        val saveAllLocal = mock<SaveAllLocal>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
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
            saveAllVisitante = saveAllVisitante
        )
        viewModel.returnDataConfig()
        assertEquals(viewModel.uiState.value.number, "16997417840")
        assertEquals(viewModel.uiState.value.password, "12345")
    }

    @Test
    fun `check return msg when field empty`() = runTest {
        val recoverConfigInternal = mock<RecoverConfigInternal>()
        val sendDataConfig = mock<SendDataConfig>()
        val saveDataConfig = mock<SaveDataConfig>()
        val cleanColab = mock<CleanColab>()
        val cleanEquip = mock<CleanEquip>()
        val cleanLocal = mock<CleanLocal>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val recoverColabServer = mock<RecoverColabServer>()
        val recoverEquipServer = mock<RecoverEquipServer>()
        val recoverLocalServer = mock<RecoverLocalServer>()
        val recoverTerceiroServer = mock<RecoverTerceiroServer>()
        val recoverVisitanteServer = mock<RecoverVisitanteServer>()
        val saveAllColab = mock<SaveAllColab>()
        val saveAllEquip = mock<SaveAllEquip>()
        val saveAllLocal = mock<SaveAllLocal>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
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
            saveAllVisitante = saveAllVisitante
        )
        viewModel.saveDataAndUpdateAllDatabase()
        assertEquals(viewModel.uiState.value.flagDialog, true)
    }

    @Test
    fun `check return failure usecase when field number type is not type number`() = runTest {
        val recoverConfigInternal = mock<RecoverConfigInternal>()
        val sendDataConfig = mock<SendDataConfig>()
        val saveDataConfig = mock<SaveDataConfig>()
        val cleanColab = mock<CleanColab>()
        val cleanEquip = mock<CleanEquip>()
        val cleanLocal = mock<CleanLocal>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val recoverColabServer = mock<RecoverColabServer>()
        val recoverEquipServer = mock<RecoverEquipServer>()
        val recoverLocalServer = mock<RecoverLocalServer>()
        val recoverTerceiroServer = mock<RecoverTerceiroServer>()
        val recoverVisitanteServer = mock<RecoverVisitanteServer>()
        val saveAllColab = mock<SaveAllColab>()
        val saveAllEquip = mock<SaveAllEquip>()
        val saveAllLocal = mock<SaveAllLocal>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(sendDataConfig(
            number = "16dfda",
            password = "12345",
            version = "6.00"
        )).thenReturn(Result.failure(UsecaseException(cause = NumberFormatException("For input string: \"1df52\""))))
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
            saveAllVisitante = saveAllVisitante
        )
        viewModel.updateNumber("16dfda")
        viewModel.updatePassword("12345")
        viewModel.saveDataAndUpdateAllDatabase()
        assertEquals(viewModel.uiState.value.errors, Errors.TOKEN)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.currentProgress, 1f)
        assertEquals(viewModel.uiState.value.msgProgress, "Error SendDataConfig -> Failure Usecase -> ${NumberFormatException("For input string: \"1df52\"")}")
        assertEquals(viewModel.uiState.value.failure, "Error SendDataConfig -> Failure Usecase -> ${NumberFormatException("For input string: \"1df52\"")}")
    }

    @Test
    fun `check return failure datasource if have errors in Datasource`() = runTest {
        val recoverConfigInternal = mock<RecoverConfigInternal>()
        val sendDataConfig = mock<SendDataConfig>()
        val saveDataConfig = mock<SaveDataConfig>()
        val cleanColab = mock<CleanColab>()
        val cleanEquip = mock<CleanEquip>()
        val cleanLocal = mock<CleanLocal>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val recoverColabServer = mock<RecoverColabServer>()
        val recoverEquipServer = mock<RecoverEquipServer>()
        val recoverLocalServer = mock<RecoverLocalServer>()
        val recoverTerceiroServer = mock<RecoverTerceiroServer>()
        val recoverVisitanteServer = mock<RecoverVisitanteServer>()
        val saveAllColab = mock<SaveAllColab>()
        val saveAllEquip = mock<SaveAllEquip>()
        val saveAllLocal = mock<SaveAllLocal>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(sendDataConfig(
            number = "16997417840",
            password = "12345",
            version = "6.00"
        )).thenReturn(Result.failure(DatasourceException(cause = NullPointerException())))
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
            saveAllVisitante = saveAllVisitante
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.saveDataAndUpdateAllDatabase()
        assertEquals(viewModel.uiState.value.errors, Errors.TOKEN)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.currentProgress, 1f)
        assertEquals(viewModel.uiState.value.msgProgress, "Error SendDataConfig -> Failure Datasource -> ${NullPointerException()}")
        assertEquals(viewModel.uiState.value.failure, "Error SendDataConfig -> Failure Datasource -> ${NullPointerException()}")
    }

    @Test
    fun `check return failure usecase in save if have errors in Save data Usecase`() = runTest {
        val recoverConfigInternal = mock<RecoverConfigInternal>()
        val sendDataConfig = mock<SendDataConfig>()
        val saveDataConfig = mock<SaveDataConfig>()
        val cleanColab = mock<CleanColab>()
        val cleanEquip = mock<CleanEquip>()
        val cleanLocal = mock<CleanLocal>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val recoverColabServer = mock<RecoverColabServer>()
        val recoverEquipServer = mock<RecoverEquipServer>()
        val recoverLocalServer = mock<RecoverLocalServer>()
        val recoverTerceiroServer = mock<RecoverTerceiroServer>()
        val recoverVisitanteServer = mock<RecoverVisitanteServer>()
        val saveAllColab = mock<SaveAllColab>()
        val saveAllEquip = mock<SaveAllEquip>()
        val saveAllLocal = mock<SaveAllLocal>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(sendDataConfig(
            number = "16997417840",
            password = "12345",
            version = "6.00"
        )).thenReturn(Result.success(1L))
        whenever(saveDataConfig(
            number = "16997417840",
            password = "12345",
            version = "6.00",
            idBD = 1L
        )).thenReturn(Result.failure(UsecaseException(cause = NullPointerException())))
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
            saveAllVisitante = saveAllVisitante
        )
        viewModel.updateNumber("16997417840")
        viewModel.updatePassword("12345")
        viewModel.saveDataAndUpdateAllDatabase()
        assertEquals(viewModel.uiState.value.errors, Errors.TOKEN)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.currentProgress, 1f)
        assertEquals(viewModel.uiState.value.msgProgress, "Error SaveDataConfig -> Failure Usecase -> ${NullPointerException()}")
        assertEquals(viewModel.uiState.value.failure, "Error SaveDataConfig -> Failure Usecase -> ${NullPointerException()}")
    }

//    @Test
//    fun `check return finally sent and save Config`() = runTest {
//        val recoverConfigInternal = mock<RecoverConfigInternal>()
//        val sendDataConfig = mock<SendDataConfig>()
//        val saveDataConfig = mock<SaveDataConfig>()
//        whenever(sendDataConfig(
//            number = "16997417840",
//            password = "12345",
//            version = "6.00"
//        )).thenReturn(Result.success(1L))
//        whenever(saveDataConfig(
//            number = "16997417840",
//            password = "12345",
//            version = "6.00",
//            idBD = 1L
//        )).thenReturn(Result.success(true))
//        val viewModel = ConfigViewModel(recoverConfigInternal, sendDataConfig, saveDataConfig)
//        viewModel.updateNumber("16997417840")
//        viewModel.updatePassword("12345")
//        viewModel.saveDataAndUpdateAllDatabase()
//        assertEquals(viewModel.uiState.value.flagDialog, false)
//        assertEquals(viewModel.uiState.value.currentProgress, 1f)
//        assertEquals(viewModel.uiState.value.msgProgress, "Ajuste iniciais finalizado com sucesso!")
//        assertEquals(viewModel.uiState.value.currentProgress, 1f)
//    }
}