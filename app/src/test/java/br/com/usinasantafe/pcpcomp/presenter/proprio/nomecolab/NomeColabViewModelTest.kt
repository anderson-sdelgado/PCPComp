package br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetMatricColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
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
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        assertEquals(viewModel.uiState.value.flowApp, FlowApp.ADD)
        assertEquals(viewModel.uiState.value.typeOcupante, TypeOcupante.MOTORISTA)
    }

    @Test
    fun `Check return failure if RecoverNomeColab have failure`() = runTest {
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(getNomeColab("19759")).thenReturn(
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
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        viewModel.returnNomeColab()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> RecoverNomeColab -> java.lang.Exception")
    }

    @Test
    fun `Check return name if RecoverNomeColab execute success`() = runTest {
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(getNomeColab("19759")).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        viewModel.returnNomeColab()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.nomeColab, "ANDERSON DA SILVA DELGADO")
    }

    @Test
    fun `Check return failure if SetMatricColab have failure in add Motorista`() = runTest {
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(setMatricColab(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.MOTORISTA,
            id = 0
        )).thenReturn(
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
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        viewModel.setMatric()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetMatricColab -> java.lang.Exception")
    }

    @Test
    fun `Check return name if SetMatricColab execute success in add Motorista`() = runTest {
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(setMatricColab(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.MOTORISTA,
            id = 0
        )).thenReturn(
            Result.success(true)
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        viewModel.setMatric()
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagDialog, false)
    }

    @Test
    fun `Check return failure if SetMatricColab have failure in add Passag`() = runTest {
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(setMatricColab(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.PASSAGEIRO,
            id = 0
        )).thenReturn(
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
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.PASSAGEIRO.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        viewModel.setMatric()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetMatricColab -> java.lang.Exception")
    }

    @Test
    fun `Check return success if SetMatricColab execute success in add Passag`() = runTest {
        val getNomeColab = mock<GetNomeColab>()
        val setMatricColab = mock<SetMatricColab>()
        whenever(setMatricColab(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.PASSAGEIRO,
            id = 0
        )).thenReturn(
            Result.success(true)
        )
        val viewModel = NomeColabViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.PASSAGEIRO.ordinal,
                    Args.ID_ARGS to 0,
                    Args.MATRIC_COLAB_ARGS to "19759"
                )
            ),
            getNomeColab,
            setMatricColab
        )
        viewModel.setMatric()
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagDialog, false)
    }

}