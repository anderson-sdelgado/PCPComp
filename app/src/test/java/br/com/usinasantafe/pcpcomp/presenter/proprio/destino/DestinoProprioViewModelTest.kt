package br.com.usinasantafe.pcpcomp.presenter.proprio.destino

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetDestinoProprio
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DestinoProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check view msg if field is empty`() {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setDestinoProprio,
            getTypeMov
        )
        viewModel.setDestino()
        assertEquals(viewModel.uiState.value.flagDialog, true)
    }

    @Test
    fun `Check return failure if have failure in SetDestino`() = runTest {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            setDestinoProprio(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetDestinoProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setDestinoProprio,
            getTypeMov
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetDestinoProprio -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have failure in GetTypeMov`() = runTest {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            setDestinoProprio(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getTypeMov()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetTypeMov",
                    cause = Exception()
                )
            )
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setDestinoProprio,
            getTypeMov
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> GetTypeMov -> java.lang.Exception")
    }

    @Test
    fun `Check return TypeMov INPUT if execute success`() = runTest {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            setDestinoProprio(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getTypeMov()
        ).thenReturn(
            Result.success(TypeMov.INPUT)
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setDestinoProprio,
            getTypeMov
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertFalse(viewModel.uiState.value.flagDialog)
        assertTrue(viewModel.uiState.value.flagAccess)
        assertEquals(viewModel.uiState.value.typeMov, TypeMov.INPUT)
    }
}