package br.com.usinasantafe.pcpcomp.presenter.chave.detalhe

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.CloseMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetDetalheMovChave
import br.com.usinasantafe.pcpcomp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetalheChaveViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getDetalheMovChave = mock<GetDetalheMovChave>()
    private val closeMovChave = mock<CloseMovChave>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle =
            SavedStateHandle(
                mapOf(
                    Args.ID_ARGS to 1
                )
            ),
    ) = DetalheChaveViewModel(
        saveStateHandle = savedStateHandle,
        getDetalheMovChave = getDetalheMovChave,
        closeMovChave = closeMovChave
    )

    @Test
    fun `recoverDetalhe - Check return failure if have error in GetDetalheMovChave`() =
        runTest {
            whenever(
                getDetalheMovChave(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetDetalheMovChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverDetalhe()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> GetDetalheMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverDetalhe - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getDetalheMovChave(1)
            ).thenReturn(
                Result.success(
                    DetalheChaveModel(
                        dthr = "08/08/2024 12:00",
                        tipoMov = "ENTRADA",
                        chave = "123456",
                        colab = "19759 - ANDERSON DA SILVA DELGADO",
                        observ = "Teste Observ"
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverDetalhe()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialog,
                false
            )
            assertEquals(
                state.dthr,
                "08/08/2024 12:00"
            )
            assertEquals(
                state.tipoMov,
                "ENTRADA"
            )
            assertEquals(
                state.chave,
                "123456"
            )
            assertEquals(
                state.colab,
                "19759 - ANDERSON DA SILVA DELGADO"
            )
            assertEquals(
                state.observ,
                "Teste Observ"
            )
        }

    @Test
    fun `closeMov - Check return failure if have error in CloseMovChave`() =
        runTest {
            whenever(
                closeMovChave(1)
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CloseMovChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.closeMov()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> CloseMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `closeMov - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                closeMovChave(1)
            ).thenReturn(
                Result.success(
                    true
                )
            )
            val viewModel = getViewModel()
            viewModel.closeMov()
            val state = viewModel.uiState.value
            assertEquals(
                state.flagDialogCheck,
                false
            )
            assertEquals(
                state.flagCloseMov,
                true
            )
        }

}