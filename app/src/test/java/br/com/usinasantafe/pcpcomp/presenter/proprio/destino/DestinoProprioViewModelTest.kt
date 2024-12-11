package br.com.usinasantafe.pcpcomp.presenter.proprio.destino

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetDestinoProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetDestinoProprio
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
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
        val getDestinoProprio = mock<GetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()

        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setDestinoProprio,
            getDestinoProprio,
            getTypeMov
        )
        viewModel.setDestino()
        assertEquals(viewModel.uiState.value.flagDialog, true)
    }

    @Test
    fun `Check return failure if have failure in SetDestino`() = runTest {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getDestinoProprio = mock<GetDestinoProprio>()
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
            getDestinoProprio,
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
        val getDestinoProprio = mock<GetDestinoProprio>()
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
            getDestinoProprio,
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
        val getDestinoProprio = mock<GetDestinoProprio>()
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
            Result.success(TypeMovEquip.INPUT)
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setDestinoProprio,
            getDestinoProprio,
            getTypeMov
        )
        viewModel.onDestinoChanged("Teste")
        viewModel.setDestino()
        assertFalse(viewModel.uiState.value.flagDialog)
        assertTrue(viewModel.uiState.value.flagAccess)
        assertEquals(viewModel.uiState.value.typeMov, TypeMovEquip.INPUT)
    }

    @Test
    fun `Check return failure if have failure in GetDestino`() = runTest {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getDestinoProprio = mock<GetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            getDestinoProprio(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetDestinoProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            setDestinoProprio,
            getDestinoProprio,
            getTypeMov
        )
        viewModel.getDestino()
        val state = viewModel.uiState.value
        assertEquals(state.flagDialog, true)
        assertEquals(state.failure, "Failure Usecase -> GetDestinoProprio -> java.lang.Exception")
    }

    @Test
    fun `Check return destino if GetDestino execute success`() = runTest {
        val setDestinoProprio = mock<SetDestinoProprio>()
        val getDestinoProprio = mock<GetDestinoProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            getDestinoProprio(
                id = 1
            )
        ).thenReturn(
            Result.success("Destino")
        )
        val viewModel = DestinoProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    Args.ID_ARGS to 1,
                )
            ),
            setDestinoProprio,
            getDestinoProprio,
            getTypeMov
        )
        viewModel.getDestino()
        val state = viewModel.uiState.value
        assertFalse(state.flagGetDestino)
        assertEquals(state.destino, "Destino")
    }
}