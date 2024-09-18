package br.com.usinasantafe.pcpcomp.presenter.residencia.motorista

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetMotoristaResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetMotoristaResidencia
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
class MotoristaResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if fields is empty`() {
        val setMotoristaResidencia = mock<SetMotoristaResidencia>()
        val getMotoristaResidencia = mock<GetMotoristaResidencia>()
        val viewModel = MotoristaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getMotoristaResidencia,
            setMotoristaResidencia
        )
        viewModel.setMotorista()
        assertTrue(viewModel.uiState.value.flagDialog)
    }

    @Test
    fun `Check return failure if have error in setMotorista`() = runTest {
        val setMotoristaResidencia = mock<SetMotoristaResidencia>()
        val getMotoristaResidencia = mock<GetMotoristaResidencia>()
        whenever(
            setMotoristaResidencia(
                motorista = "Anderson",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetMotoristaResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = MotoristaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getMotoristaResidencia,
            setMotoristaResidencia
        )
        viewModel.onMotoristaChanged("Anderson")
        viewModel.setMotorista()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> SetMotoristaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if setMotorista execute successfully`() = runTest {
        val setMotoristaResidencia = mock<SetMotoristaResidencia>()
        val getMotoristaResidencia = mock<GetMotoristaResidencia>()
        whenever(
            setMotoristaResidencia(
                motorista = "Anderson",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = MotoristaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            getMotoristaResidencia,
            setMotoristaResidencia
        )
        viewModel.onMotoristaChanged("Anderson")
        viewModel.setMotorista()
        val state = viewModel.uiState.value
        assertTrue(state.flagAccess)
    }

    @Test
    fun `Check return failure if have error in GetMotorista`() = runTest {
        val setMotoristaResidencia = mock<SetMotoristaResidencia>()
        val getMotoristaResidencia = mock<GetMotoristaResidencia>()
        whenever(
            getMotoristaResidencia(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetMotoristaResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = MotoristaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            getMotoristaResidencia,
            setMotoristaResidencia
        )
        viewModel.recoverMotorista()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> GetMotoristaResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return motorista if GetMotorista execute successfully`() = runTest {
        val setMotoristaResidencia = mock<SetMotoristaResidencia>()
        val getMotoristaResidencia = mock<GetMotoristaResidencia>()
        whenever(
            getMotoristaResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("Anderson")
        )
        val viewModel = MotoristaResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1
                )
            ),
            getMotoristaResidencia,
            setMotoristaResidencia
        )
        viewModel.recoverMotorista()
        val state = viewModel.uiState.value
        assertEquals(state.motorista, "Anderson")
    }
}