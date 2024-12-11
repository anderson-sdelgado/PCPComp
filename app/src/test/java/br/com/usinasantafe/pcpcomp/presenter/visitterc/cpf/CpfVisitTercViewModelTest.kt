package br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateVisitante
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CheckCpfVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetCpfVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetTitleCpfVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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

    private val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
    private val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
    private val getCpfVisitTerc = mock<GetCpfVisitTerc>()
    private val updateTerceiro = mock<UpdateTerceiro>()
    private val updateVisitante = mock<UpdateVisitante>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = CpfVisitTercViewModel(
        savedStateHandle,
        getTitleCpfVisitTerc,
        checkCpfVisitTerc,
        getCpfVisitTerc,
        updateTerceiro,
        updateVisitante
    )
    private val sizeAll = 7f

    @Test
    fun `Check return failure if fields is empty`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
        )
        viewModel.setTextField("", TypeButton.OK)
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertTrue(state.flagFailure)
        assertEquals(Errors.FIELDEMPTY, state.errors)
    }

    @Test
    fun `Check adjustment of cpf in typing`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            )
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
    fun `Check return failure if have error in CleanTerceiro`() = runTest {
        val qtdBefore = 0f
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
                    currentProgress = 1f,
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
            updateTerceiro,
            updateVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), ((qtdBefore * 3) + 2).toInt())
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(((qtdBefore * 3) + 1), sizeAll)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanTerceiro -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `Check return failure if have error in CleanVisitante`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(1f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(2f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(3f, sizeAll)
                ),
            )
        )
        whenever(
            updateVisitante(
                sizeAll = sizeAll,
                count = 2f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(4f, sizeAll)
                ),
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                    currentProgress = 1f,
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
            updateTerceiro,
            updateVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 5)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(4f, 7f)
            )
        )
        assertEquals(
            result[4],
            CpfVisitTercState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanVisitante -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `Check return success if UpdateAllTable execute successfully`() = runTest {
        val getTitleCpfVisitTerc = mock<GetTitleCpfVisitTerc>()
        val checkCpfVisitTerc = mock<CheckCpfVisitTerc>()
        val getCpfVisitTerc = mock<GetCpfVisitTerc>()
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
        whenever(
            updateTerceiro(
                sizeAll = sizeAll,
                count = 1f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_terceiro",
                    currentProgress = percentage(1f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                    currentProgress = percentage(2f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_terceiro",
                    currentProgress = percentage(3f, sizeAll)
                ),
            )
        )
        whenever(
            updateVisitante(
                sizeAll = sizeAll,
                count = 2f
            )
        ).thenReturn(
            flowOf(
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_visitante",
                    currentProgress = percentage(4f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                    currentProgress = percentage(5f, sizeAll)
                ),
                ResultUpdate(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_visitante",
                    currentProgress = percentage(6f, sizeAll)
                ),
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
            updateTerceiro,
            updateVisitante
        )
        viewModel.setTextField("", TypeButton.UPDATE)
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 7)
        assertEquals(
            result[0],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_terceiro",
                currentProgress = percentage(1f, 7f)
            )
        )
        assertEquals(
            result[1],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_terceiro do Web Service",
                currentProgress = percentage(2f, 7f)
            )
        )
        assertEquals(
            result[2],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_terceiro",
                currentProgress = percentage(3f, 7f)
            )
        )
        assertEquals(
            result[3],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_visitante",
                currentProgress = percentage(4f, 7f)
            )
        )
        assertEquals(
            result[4],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_visitante do Web Service",
                currentProgress = percentage(5f, 7f)
            )
        )
        assertEquals(
            result[5],
            CpfVisitTercState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_visitante",
                currentProgress = percentage(6f, 7f)
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
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
        val updateTerceiro = mock<UpdateTerceiro>()
        val updateVisitante = mock<UpdateVisitante>()
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
            updateTerceiro,
            updateVisitante
        )
        viewModel.recoverTitle()
        val state = viewModel.uiState.value
        assertEquals(state.title, "CPF VISITANTE")
    }
}