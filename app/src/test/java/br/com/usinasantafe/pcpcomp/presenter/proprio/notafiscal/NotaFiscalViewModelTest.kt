package br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetNotaFiscalProprio
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButtonWithoutUpdate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NotaFiscalViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun check_access_true_if_button_ok_pressed() {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setNotaFiscalProprio
        )
        viewModel.setTextField("OK", TypeButtonWithoutUpdate.OK)
        assertTrue(viewModel.uiState.value.flagAccess)
    }

    @Test
    fun `Check return failure if have failure in SetNotaFiscalProprio`() = runTest {
        val setNotaFiscalProprio = mock<SetNotaFiscalProprio>()
        whenever(
            setNotaFiscalProprio(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetNotaFiscalProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = NotaFiscalViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setNotaFiscalProprio
        )
        viewModel.setTextField("123456", TypeButtonWithoutUpdate.NUMERIC)
        viewModel.setTextField("OK", TypeButtonWithoutUpdate.OK)
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetNotaFiscalProprio -> java.lang.Exception")
    }
}