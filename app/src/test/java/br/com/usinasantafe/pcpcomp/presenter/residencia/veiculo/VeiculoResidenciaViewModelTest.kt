package br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetVeiculoResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetVeiculoResidencia
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
class VeiculoResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if fields is empty`() {
        val setVeiculoResidencia = mock<SetVeiculoResidencia>()
        val getVeiculoResidencia = mock<GetVeiculoResidencia>()
        val viewModel = VeiculoResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getVeiculoResidencia,
            setVeiculoResidencia
        )
        viewModel.setVeiculo()
        assertTrue(viewModel.uiState.value.flagDialog)
    }

    @Test
    fun `Check return failure if have error in setVeiculo`() = runTest {
        val setVeiculoResidencia = mock<SetVeiculoResidencia>()
        val getVeiculoResidencia = mock<GetVeiculoResidencia>()
        whenever(
            setVeiculoResidencia(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetVeiculoResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = VeiculoResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getVeiculoResidencia,
            setVeiculoResidencia
        )
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SetVeiculoResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if setVeiculo execute successfully`() = runTest {
        val setVeiculoResidencia = mock<SetVeiculoResidencia>()
        val getVeiculoResidencia = mock<GetVeiculoResidencia>()
        whenever(
            setVeiculoResidencia(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = VeiculoResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getVeiculoResidencia,
            setVeiculoResidencia
        )
        viewModel.onVeiculoChanged("GOL")
        viewModel.setVeiculo()
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have error in GetVeiculo`() = runTest {
        val setVeiculoResidencia = mock<SetVeiculoResidencia>()
        val getVeiculoResidencia = mock<GetVeiculoResidencia>()
        whenever(
            getVeiculoResidencia(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetVeiculoResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = VeiculoResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            getVeiculoResidencia,
            setVeiculoResidencia
        )
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> GetVeiculoResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return veiculo if GetVeiculo execute successfully`() = runTest {
        val setVeiculoResidencia = mock<SetVeiculoResidencia>()
        val getVeiculoResidencia = mock<GetVeiculoResidencia>()
        whenever(
            getVeiculoResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("GOL")
        )
        val viewModel = VeiculoResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            getVeiculoResidencia,
            setVeiculoResidencia
        )
        viewModel.recoverVeiculo()
        val state = viewModel.uiState.value
        assertEquals(state.veiculo, "GOL")
    }
}