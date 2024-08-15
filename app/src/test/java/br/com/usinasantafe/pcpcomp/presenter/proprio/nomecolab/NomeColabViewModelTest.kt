package br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.RecoverNomeColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetMatricColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NomeColabViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check set fields initial`() {
        val recoverNomeColab = mock<RecoverNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to 0,
                    Args.TYPE_OCUPANTE_ARGS to 0,
                    Args.POS_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            recoverNomeColab,
            setMatricColab
        )
        assertEquals(viewModel.uiState.value.flowApp, 0)
        assertEquals(viewModel.uiState.value.typeOcupante, 0)
    }

    @Test
    fun `Check return failure if RecoverNomeColab have failure`() = runTest {
        val recoverNomeColab = mock<RecoverNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(recoverNomeColab("19759")).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverNomeColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to 0,
                    Args.TYPE_OCUPANTE_ARGS to 0,
                    Args.POS_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            recoverNomeColab,
            setMatricColab
        )
        viewModel.returnNomeColab()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> RecoverNomeColab -> java.lang.Exception")
    }

    @Test
    fun `Check return name if RecoverNomeColab execute success`() = runTest {
        val recoverNomeColab = mock<RecoverNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(recoverNomeColab("19759")).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to 0,
                    Args.TYPE_OCUPANTE_ARGS to 0,
                    Args.POS_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            recoverNomeColab,
            setMatricColab
        )
        viewModel.returnNomeColab()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.nomeColab, "ANDERSON DA SILVA DELGADO")
    }

    @Test
    fun `Check return failure if SetMatricColab have failure`() = runTest {
        val recoverNomeColab = mock<RecoverNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(setMatricColab("19759")).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetMatricColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to 0,
                    Args.TYPE_OCUPANTE_ARGS to 0,
                    Args.POS_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            recoverNomeColab,
            setMatricColab
        )
        viewModel.setMatric()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetMatricColab -> java.lang.Exception")
    }

    @Test
    fun `Check return name if SetMatricColab execute success`() = runTest {
        val recoverNomeColab = mock<RecoverNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(setMatricColab("19759")).thenReturn(
            Result.success(true)
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to 0,
                    Args.TYPE_OCUPANTE_ARGS to 0,
                    Args.POS_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            recoverNomeColab,
            setMatricColab
        )
        viewModel.setMatric()
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagDialog, false)
    }

}