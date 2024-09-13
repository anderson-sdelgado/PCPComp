package br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllTerceiroServer
import br.com.usinasantafe.pcpcomp.domain.usecases.getserver.GetAllVisitanteServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CheckCpfVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetCpfVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetTitleCpfVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CpfVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

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

    @Test
    fun `Check return failure if fields is empty`() {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertTrue(state.flagFailure)
        assertEquals(Errors.FIELDEMPTY, state.errors)
    }

    @Test
    fun `Check adjustment of cpf in typing`() {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("1", TypeButton.NUMERIC)
        viewModel.setTextField("2", TypeButton.NUMERIC)
        viewModel.setTextField("3", TypeButton.NUMERIC)
        viewModel.setTextField("4", TypeButton.NUMERIC)
        viewModel.setTextField("5", TypeButton.NUMERIC)
        viewModel.setTextField("6", TypeButton.NUMERIC)
        viewModel.setTextField("7", TypeButton.NUMERIC)
        viewModel.setTextField("8", TypeButton.NUMERIC)
        viewModel.setTextField("9", TypeButton.NUMERIC)
        viewModel.setTextField("0", TypeButton.NUMERIC)
        viewModel.setTextField("0", TypeButton.NUMERIC)
        val state = viewModel.uiState.value
        assertEquals("123.456.789-00", state.cpf)
    }

    @Test
    fun `Check return failure if have error in CleanColab`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanTerceiro",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanTerceiro -> java.lang.Exception",
                msgProgress = "Failure Usecase -> CleanTerceiro -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanTerceiro -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in GetAllTerceiroServer`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.success(true)
        )
        whenever(getAllTerceiroServer()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetAllTerceiroServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> GetAllTerceiroServer -> java.lang.Exception",
                msgProgress = "Failure Usecase -> GetAllTerceiroServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> GetAllTerceiroServer -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in SaveAllTerceiro`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.success(true)
        )
        whenever(getAllTerceiroServer()).thenReturn(
            Result.success(terceiroList)
        )
        whenever(saveAllTerceiro(terceiroList)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveAllTerceiro",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> SaveAllTerceiro -> java.lang.Exception",
                msgProgress = "Failure Usecase -> SaveAllTerceiro -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> SaveAllTerceiro -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in CleanVisitante`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.success(true)
        )
        whenever(getAllTerceiroServer()).thenReturn(
            Result.success(terceiroList)
        )
        whenever(saveAllTerceiro(terceiroList)).thenReturn(
            Result.success(true)
        )
        whenever(cleanVisitante()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanVisitante",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 5)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(4f, 7f)
            )
        )
        assertEquals(
            result[4],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanVisitante -> java.lang.Exception",
                msgProgress = "Failure Usecase -> CleanVisitante -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanVisitante -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in GetAllVisitanteServer`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.success(true)
        )
        whenever(getAllTerceiroServer()).thenReturn(
            Result.success(terceiroList)
        )
        whenever(saveAllTerceiro(terceiroList)).thenReturn(
            Result.success(true)
        )
        whenever(cleanVisitante()).thenReturn(
            Result.success(true)
        )
        whenever(getAllVisitanteServer()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetAllVisitanteServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 6)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(4f, 7f)
            )
        )
        assertEquals(
            result[4],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(5f, 7f)
            )
        )
        assertEquals(
            result[5],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> GetAllVisitanteServer -> java.lang.Exception",
                msgProgress = "Failure Usecase -> GetAllVisitanteServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> GetAllVisitanteServer -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have error in SaveAllVisitante`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.success(true)
        )
        whenever(getAllTerceiroServer()).thenReturn(
            Result.success(terceiroList)
        )
        whenever(saveAllTerceiro(terceiroList)).thenReturn(
            Result.success(true)
        )
        whenever(cleanVisitante()).thenReturn(
            Result.success(true)
        )
        whenever(getAllVisitanteServer()).thenReturn(
            Result.success(visitanteList)
        )
        whenever(saveAllVisitante(visitanteList)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveAllVisitante",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 7)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(4f, 7f)
            )
        )
        assertEquals(
            result[4],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(5f, 7f)
            )
        )
        assertEquals(
            result[5],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = porc(6f, 7f)
            )
        )
        assertEquals(
            result[6],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> SaveAllVisitante -> java.lang.Exception",
                msgProgress = "Failure Usecase -> SaveAllVisitante -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> SaveAllVisitante -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return success if UpdateAllTable execute successfully`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(cleanTerceiro()).thenReturn(
            Result.success(true)
        )
        whenever(getAllTerceiroServer()).thenReturn(
            Result.success(terceiroList)
        )
        whenever(saveAllTerceiro(terceiroList)).thenReturn(
            Result.success(true)
        )
        whenever(cleanVisitante()).thenReturn(
            Result.success(true)
        )
        whenever(getAllVisitanteServer()).thenReturn(
            Result.success(visitanteList)
        )
        whenever(saveAllVisitante(visitanteList)).thenReturn(
            Result.success(true)
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 7)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = porc(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = porc(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = porc(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = porc(4f, 7f)
            )
        )
        assertEquals(
            result[4],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = porc(5f, 7f)
            )
        )
        assertEquals(
            result[5],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = porc(6f, 7f)
            )
        )
        assertEquals(
            result[6],
            CpfVisitTercState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Atualização de dados realizado com sucesso!"
        )
    }

    @Test
    fun `Check return failure if have error in CheckCpfVisitTerc`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(
            checkCpfVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CheckCpfVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("123.456.789-00", TypeButton.NUMERIC)
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        assertEquals(state.flagDialog, true)
        assertEquals(state.flagFailure, true)
        assertEquals(state.errors, Errors.EXCEPTION)
        assertEquals(state.failure, "Failure Usecase -> CheckCpfVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return true if cpf is correct`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(
            checkCpfVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("123.456.789-00", TypeButton.NUMERIC)
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        assertEquals(state.flagAccess, true)
        assertEquals(state.flagDialog, false)
        assertEquals(state.flagFailure, false)
    }

    @Test
    fun `Check return false if cpf is incorrect`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(
            checkCpfVisitTerc(
                cpf = "123.456.789-00",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(false)
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.setTextField("123.456.789-00", TypeButton.NUMERIC)
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        assertEquals(state.flagAccess, false)
        assertEquals(state.flagDialog, true)
        assertEquals(state.flagFailure, true)
    }

    @Test
    fun `Check return failure if have error in GetCpfVisitTerc`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(
            getCpfVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetCpfVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.getCpf()
        val state = viewModel.uiState.value
        assertEquals(state.flagDialog, true)
        assertEquals(state.flagFailure, true)
        assertEquals(state.errors, Errors.EXCEPTION)
        assertEquals(state.failure, "Failure Usecase -> GetCpfVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return cpf if GetCpfVisitTerc execute successfully`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(
            getCpfVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("123.456.789-00")
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.getCpf()
        val state = viewModel.uiState.value
        assertEquals(state.cpf, "123.456.789-00")
        assertEquals(state.checkGetCpf, false)
    }

    @Test
    fun `Check return failure if have error in GetTitleCpfVisitTerc`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(getTitleCpfVisitTerc(
            flowApp = FlowApp.ADD,
            id = 0
        )).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetTitleCpfVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.recoverTitle()
        val state = viewModel.uiState.value
        assertEquals(state.flagDialog, true)
        assertEquals(state.flagFailure, true)
        assertEquals(state.errors, Errors.EXCEPTION)
        assertEquals(state.failure, "Failure Usecase -> GetTitleCpfVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return title if GetTitleCpfVisitTerc execute successfully`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val cleanTerceiro = mock<CleanTerceiro>()
        val cleanVisitante = mock<CleanVisitante>()
        val getAllTerceiroServer = mock<GetAllTerceiroServer>()
        val getAllVisitanteServer = mock<GetAllVisitanteServer>()
        val saveAllTerceiro = mock<SaveAllTerceiro>()
        val saveAllVisitante = mock<SaveAllVisitante>()
        whenever(getTitleCpfVisitTerc(
            flowApp = FlowApp.ADD,
            id = 0
        )).thenReturn(
            Result.success("CPF VISITANTE")
        )
        val viewModel = CpfVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getTitleCpfVisitTerc,
            checkCpfVisitTerc,
            getCpfVisitTerc,
            cleanTerceiro,
            cleanVisitante,
            getAllTerceiroServer,
            getAllVisitanteServer,
            saveAllTerceiro,
            saveAllVisitante
        )
        viewModel.recoverTitle()
        val state = viewModel.uiState.value
        assertEquals(state.title, "CPF VISITANTE")
    }
}