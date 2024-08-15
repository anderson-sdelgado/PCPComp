package br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CleanPassagColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.RecoverNomeColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetMatricColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PassagColabListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if CleanPassagColab have failure`() = runTest {
        val cleanPassagColab = mock<CleanPassagColab>()
        whenever(cleanPassagColab()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanPassagColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagColabListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to 0,
                    Args.TYPE_OCUPANTE_ARGS to 0,
                    Args.POS_ARGS to 0,
                )
            ),
            cleanPassagColab
        )
        viewModel.cleanPassag()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> CleanPassagColab -> java.lang.Exception")
    }

}