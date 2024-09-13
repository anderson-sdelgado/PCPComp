package br.com.usinasantafe.pcpcomp.presenter.visitterc.placa

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetPlacaVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetPlacaVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PlacaVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if field is empty`() {
        val setPlacaVisitTerc = mock<SetPlacaVisitTerc>()
        val getPlacaVisitTerc = mock<GetPlacaVisitTerc>()
        val viewModel = PlacaVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setPlacaVisitTerc,
            getPlacaVisitTerc
        )
        viewModel.setPlaca()
        assertTrue(viewModel.uiState.value.flagDialog)
    }

    @Test
    fun `Check return failure if have error in SetPlaca`() = runTest {
        val setPlacaVisitTerc = mock<SetPlacaVisitTerc>()
        val getPlacaVisitTerc = mock<GetPlacaVisitTerc>()
        whenever(
            setPlacaVisitTerc(
                placa = "AAA0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "setPlacaVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = PlacaVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setPlacaVisitTerc,
            getPlacaVisitTerc
        )
        viewModel.onPlacaChanged("AAA0000")
        viewModel.setPlaca()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> setPlacaVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return true if SetPlaca execute success`() = runTest {
        val setPlacaVisitTerc = mock<SetPlacaVisitTerc>()
        val getPlacaVisitTerc = mock<GetPlacaVisitTerc>()
        whenever(
            setPlacaVisitTerc(
                placa = "AAA0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = PlacaVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setPlacaVisitTerc,
            getPlacaVisitTerc
        )
        viewModel.onPlacaChanged("AAA0000")
        viewModel.setPlaca()
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have error in GetPlaca`() = runTest {
        val setPlacaVisitTerc = mock<SetPlacaVisitTerc>()
        val getPlacaVisitTerc = mock<GetPlacaVisitTerc>()
        whenever(
            getPlacaVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetPlacaVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = PlacaVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setPlacaVisitTerc,
            getPlacaVisitTerc
        )
        viewModel.recoverPlaca()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> GetPlacaVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return placa if GetPlaca execute successfully`() = runTest {
        val setPlacaVisitTerc = mock<SetPlacaVisitTerc>()
        val getPlacaVisitTerc = mock<GetPlacaVisitTerc>()
        whenever(
            getPlacaVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("AAA0000")
        )
        val viewModel = PlacaVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setPlacaVisitTerc,
            getPlacaVisitTerc
        )
        viewModel.recoverPlaca()
        val state = viewModel.uiState.value
        assertEquals(state.placa, "AAA0000")
    }
}