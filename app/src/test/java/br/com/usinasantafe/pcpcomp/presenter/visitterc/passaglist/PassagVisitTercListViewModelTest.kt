package br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CleanPassagVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.DeletePassagVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetPassagVisitTercList
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PassagVisitTercListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in CleanPassagVisitTerc`() = runTest {
        val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
        val getPassagVisitTercList = mock<GetPassagVisitTercList>()
        val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()
        whenever(cleanPassagVisitTerc()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanPassagVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagVisitTercListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagVisitTerc,
            getPassagVisitTercList,
            deletePassagVisitTerc
        )
        viewModel.cleanPassag()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> CleanPassagVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CleanPassagVisitTerc execute successfully`() = runTest {
        val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
        val getPassagVisitTercList = mock<GetPassagVisitTercList>()
        val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()
        whenever(cleanPassagVisitTerc()).thenReturn(
            Result.success(true)
        )
        val viewModel = PassagVisitTercListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagVisitTerc,
            getPassagVisitTercList,
            deletePassagVisitTerc
        )
        viewModel.cleanPassag()
        val state = viewModel.uiState.value
        assertFalse(state.flagDialog)
        assertFalse(state.flagClean)
    }

    @Test
    fun `Check return failure if have error in GetPassagVisitTercList`() = runTest {
        val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
        val getPassagVisitTercList = mock<GetPassagVisitTercList>()
        val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()
        whenever(
            getPassagVisitTercList(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetPassagVisitTercList",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagVisitTercListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagVisitTerc,
            getPassagVisitTercList,
            deletePassagVisitTerc
        )
        viewModel.recoverPassag()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> GetPassagVisitTercList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if GetPassagVisitTercList execute successfully`() = runTest {
        val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
        val getPassagVisitTercList = mock<GetPassagVisitTercList>()
        val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()
        whenever(
            getPassagVisitTercList(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    PassagVisitTercModel(
                        id = 1,
                        cpf = "123.456.789-00",
                        nome = "Nome"
                    )
                )
            )
        )
        val viewModel = PassagVisitTercListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagVisitTerc,
            getPassagVisitTercList,
            deletePassagVisitTerc
        )
        viewModel.recoverPassag()
        val state = viewModel.uiState.value
        assertFalse(state.flagDialog)
        assertEquals(state.passagList.size, 1)
        assertEquals(state.passagList[0].id, 1)
        assertEquals(state.passagList[0].cpf, "123.456.789-00")
        assertEquals(state.passagList[0].nome, "Nome")
    }

    @Test
    fun `Check return failure if have error in DeletePassagVisitTerc`() = runTest {
        val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
        val getPassagVisitTercList = mock<GetPassagVisitTercList>()
        val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()
        whenever(
            deletePassagVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "DeletePassagVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagVisitTercListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagVisitTerc,
            getPassagVisitTercList,
            deletePassagVisitTerc
        )
        viewModel.setDelete(1)
        viewModel.deletePassag()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> DeletePassagVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if DeletePassagVisitTerc execute successfully`() = runTest {
        val cleanPassagVisitTerc = mock<CleanPassagVisitTerc>()
        val getPassagVisitTercList = mock<GetPassagVisitTercList>()
        val deletePassagVisitTerc = mock<DeletePassagVisitTerc>()
        whenever(
            deletePassagVisitTerc(
                idVisitTerc = 1,
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getPassagVisitTercList(
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    PassagVisitTercModel(
                        id = 1,
                        cpf = "123.456.789-00",
                        nome = "Nome"
                    )
                )
            )
        )
        val viewModel = PassagVisitTercListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagVisitTerc,
            getPassagVisitTercList,
            deletePassagVisitTerc
        )
        viewModel.setDelete(1)
        viewModel.deletePassag()
        val state = viewModel.uiState.value
        assertFalse(state.flagDialogCheck)
        assertFalse(state.flagDialog)
        assertEquals(state.passagList.size, 1)
        assertEquals(state.passagList[0].id, 1)
        assertEquals(state.passagList[0].cpf, "123.456.789-00")
        assertEquals(state.passagList[0].nome, "Nome")
    }
}