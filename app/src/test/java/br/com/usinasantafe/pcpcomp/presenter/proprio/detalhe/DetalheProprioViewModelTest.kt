package br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.RecoverDetalheProprio
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
        val recoverDetalheProprio = mock<RecoverDetalheProprio>()
        whenever(recoverDetalheProprio(1)).thenReturn(
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
            recoverDetalheProprio
        )
        viewModel.recoverDetalhe()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> RecoverDetalheProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return model if recoverDetalhe execute correctly`() = runTest {
        val recoverDetalheProprio = mock<RecoverDetalheProprio>()
        whenever(recoverDetalheProprio(1)).thenReturn(
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
            recoverDetalheProprio
        )
        viewModel.recoverDetalhe()
        assertEquals(viewModel.uiState.value.dthr, "08/08/2024 12:00")
        assertEquals(viewModel.uiState.value.tipoMov, "ENTRADA")
    }
}