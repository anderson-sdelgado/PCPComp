package br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CloseMovProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetDetalheProprio
import br.com.usinasantafe.pcpcomp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in recoverDetalhe`() = runTest {
        val getDetalheProprio = mock<GetDetalheProprio>()
        val closeMovProprio = mock<CloseMovProprio>()
        whenever(getDetalheProprio(1)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverDetalheProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = DetalheProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheProprio,
            closeMovProprio
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> RecoverDetalheProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        val getDetalheProprio = mock<GetDetalheProprio>()
        val closeMovProprio = mock<CloseMovProprio>()
        whenever(getDetalheProprio(1)).thenReturn(
            Result.success(
                DetalheProprioModel(
                    dthr = "08/08/2024 12:00",
                    tipoMov = "ENTRADA",
                    veiculo = "2300",
                    veiculoSec = "2301 - 2302",
                    motorista = "19759 - ANDERSON DA SILVA DELGADO",
                    passageiro = "19035 - JOSE DONIZETE; 18017 - RONALDO;",
                    notaFiscal = "123456",
                    observ = "Teste Observ",
                    destino = "Teste Destino"
                )
            )
        )
        val viewModel = DetalheProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheProprio,
            closeMovProprio
        )
        viewModel.recoverDetalhe()
        val state = viewModel.uiState.value
        assertEquals(state.dthr, "08/08/2024 12:00")
        assertEquals(state.tipoMov, "ENTRADA")
    }

    @Test
    fun `Check return failure if have error in CloseMovProprioOpen`() = runTest {
        val getDetalheProprio = mock<GetDetalheProprio>()
        val closeMovProprio = mock<CloseMovProprio>()
        whenever(closeMovProprio(1)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CloseMovProprioOpen",
                    cause = Exception()
                )
            )
        )
        val viewModel = DetalheProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheProprio,
            closeMovProprio
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> CloseMovProprioOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseMovProprioOpen execute correctly`() = runTest {
        val getDetalheProprio = mock<GetDetalheProprio>()
        val closeMovProprio = mock<CloseMovProprio>()
        whenever(closeMovProprio(1)).thenReturn(
            Result.success(true)
        )
        val viewModel = DetalheProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
            getDetalheProprio,
            closeMovProprio
        )
        viewModel.closeMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagCloseMov)
    }
}