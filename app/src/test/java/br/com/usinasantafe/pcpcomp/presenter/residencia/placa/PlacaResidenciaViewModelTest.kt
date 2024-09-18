package br.com.usinasantafe.pcpcomp.presenter.residencia.placa

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetPlacaResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetPlacaResidencia
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
class PlacaResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if fields is empty`() {
        val setPlacaResidencia = mock<SetPlacaResidencia>()
        val getPlacaResidencia = mock<GetPlacaResidencia>()
        val viewModel = PlacaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getPlacaResidencia,
            setPlacaResidencia
        )
        viewModel.setPlaca()
        assertTrue(viewModel.uiState.value.flagDialog)
    }

    @Test
    fun `Check return failure if have error in setPlaca`() = runTest {
        val setPlacaResidencia = mock<SetPlacaResidencia>()
        val getPlacaResidencia = mock<GetPlacaResidencia>()
        whenever(
            setPlacaResidencia(
                placa = "AAA-0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetPlacaResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = PlacaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getPlacaResidencia,
            setPlacaResidencia
        )
        viewModel.onPlacaChanged("AAA-0000")
        viewModel.setPlaca()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SetPlacaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if setPlaca execute successfully`() = runTest {
        val setPlacaResidencia = mock<SetPlacaResidencia>()
        val getPlacaResidencia = mock<GetPlacaResidencia>()
        whenever(
            setPlacaResidencia(
                placa = "AAA-0000",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = PlacaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getPlacaResidencia,
            setPlacaResidencia
        )
        viewModel.onPlacaChanged("AAA-0000")
        viewModel.setPlaca()
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have error in GetPlaca`() = runTest {
        val setPlacaResidencia = mock<SetPlacaResidencia>()
        val getPlacaResidencia = mock<GetPlacaResidencia>()
        whenever(
            getPlacaResidencia(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetPlacaResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = PlacaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            getPlacaResidencia,
            setPlacaResidencia
        )
        viewModel.recoverPlaca()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> GetPlacaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return placa if GetPlaca execute successfully`() = runTest {
        val setPlacaResidencia = mock<SetPlacaResidencia>()
        val getPlacaResidencia = mock<GetPlacaResidencia>()
        whenever(
            getPlacaResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("AAA-0000")
        )
        val viewModel = PlacaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            getPlacaResidencia,
            setPlacaResidencia
        )
        viewModel.recoverPlaca()
        val state = viewModel.uiState.value
        assertEquals(state.placa, "AAA-0000")
    }
}