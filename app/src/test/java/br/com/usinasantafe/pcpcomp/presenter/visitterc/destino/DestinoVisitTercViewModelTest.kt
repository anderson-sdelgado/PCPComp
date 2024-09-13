package br.com.usinasantafe.pcpcomp.presenter.visitterc.destino

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetDestinoVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetPlacaVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetDestinoVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetPlacaVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.presenter.visitterc.placa.PlacaVisitTercViewModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DestinoVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in GetDestino`() = runTest {
        val setDestinoVisitTerc = mock<SetDestinoVisitTerc>()
        val getDestinoVisitTerc = mock<GetDestinoVisitTerc>()
        whenever(
            getDestinoVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetDestinoVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = DestinoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setDestinoVisitTerc,
            getDestinoVisitTerc
        )
        viewModel.recoverDestino()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> GetDestinoVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return destino if GetDestino execute successfully`() = runTest {
        val setDestinoVisitTerc = mock<SetDestinoVisitTerc>()
        val getDestinoVisitTerc = mock<GetDestinoVisitTerc>()
        whenever(
            getDestinoVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("Destino")
        )
        val viewModel = DestinoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setDestinoVisitTerc,
            getDestinoVisitTerc
        )
        viewModel.recoverDestino()
        val state = viewModel.uiState.value
        assertEquals(state.destino, "Destino")
        assertEquals(state.checkGetDestino, false)
    }

    @Test
    fun `Check return failure if destino is empty`() = runTest {
        val setDestinoVisitTerc = mock<SetDestinoVisitTerc>()
        val getDestinoVisitTerc = mock<GetDestinoVisitTerc>()
        val viewModel = DestinoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setDestinoVisitTerc,
            getDestinoVisitTerc
        )
        viewModel.setDestino()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
    }

    @Test
    fun `Check return failure if have error in SetDestino`() = runTest {
        val setDestinoVisitTerc = mock<SetDestinoVisitTerc>()
        val getDestinoVisitTerc = mock<GetDestinoVisitTerc>()
        whenever(
            setDestinoVisitTerc(
                destino = "Destino",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetDestinoVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = DestinoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setDestinoVisitTerc,
            getDestinoVisitTerc
        )
        viewModel.onDestinoChanged("Destino")
        viewModel.setDestino()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SetDestinoVisitTerc -> java.lang.Exception")
    }


    @Test
    fun `Check return true if SetDestino execute successfully`() = runTest {
        val setDestinoVisitTerc = mock<SetDestinoVisitTerc>()
        val getDestinoVisitTerc = mock<GetDestinoVisitTerc>()
        whenever(
            setDestinoVisitTerc(
                destino = "Destino",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = DestinoVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            setDestinoVisitTerc,
            getDestinoVisitTerc
        )
        viewModel.onDestinoChanged("Destino")
        viewModel.setDestino()
        val state = viewModel.uiState.value
        assertTrue(state.flagAccess)
    }
}