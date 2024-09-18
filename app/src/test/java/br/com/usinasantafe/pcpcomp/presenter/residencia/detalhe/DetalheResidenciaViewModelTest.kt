package br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.CloseMovResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetDetalheResidencia
import br.com.usinasantafe.pcpcomp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in recoverDetalhe`() = runTest {
        val getDetalheResidencia = mock<GetDetalheResidencia>()
        val closeMovResidencia = mock<CloseMovResidencia>()
        whenever(getDetalheResidencia(1)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverDetalheResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = DetalheResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheResidencia,
            closeMovResidencia
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> RecoverDetalheResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        val getDetalheResidencia = mock<GetDetalheResidencia>()
        val closeMovResidencia = mock<CloseMovResidencia>()
        whenever(getDetalheResidencia(1)).thenReturn(
            Result.success(
                DetalheResidenciaModel(
                    id = 1,
                    dthr = "08/08/2024 12:00",
                    tipoMov = "ENTRADA",
                    veiculo = "GOL",
                    placa = "AAA-0000",
                    motorista = "19759 - ANDERSON DA SILVA DELGADO",
                    observ = "Teste Observ",
                )
            )
        )
        val viewModel = DetalheResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                ),
            ),
            getDetalheResidencia,
            closeMovResidencia
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertEquals(state.dthr, "08/08/2024 12:00")
        assertEquals(state.tipoMov, "ENTRADA")
    }

    @Test
    fun `Check return failure if have error in CloseMovResidenciaOpen`() = runTest {
        val getDetalheResidencia = mock<GetDetalheResidencia>()
        val closeMovResidencia = mock<CloseMovResidencia>()
        whenever(closeMovResidencia(1)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CloseMovResidenciaOpen",
                    cause = Exception()
                )
            )
        )
        val viewModel = DetalheResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                ),
                ),
            getDetalheResidencia,
            closeMovResidencia
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> CloseMovResidenciaOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseMovResidenciaOpen execute correctly`() = runTest {
        val getDetalheResidencia = mock<GetDetalheResidencia>()
        val closeMovResidencia = mock<CloseMovResidencia>()
        whenever(closeMovResidencia(1)).thenReturn(
            Result.success(true)
        )
        val viewModel = DetalheResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                ),
            ),
            getDetalheResidencia,
            closeMovResidencia
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagCloseMov)
    }
}