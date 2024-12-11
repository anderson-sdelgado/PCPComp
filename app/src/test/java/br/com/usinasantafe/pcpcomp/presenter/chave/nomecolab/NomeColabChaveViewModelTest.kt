package br.com.usinasantafe.pcpcomp.presenter.chave.nomecolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SetMatricColabMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class NomeColabChaveViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getNomeColab = mock<GetNomeColab>()
    private val setMatricColabMovChave = mock<SetMatricColabMovChave>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.MATRIC_COLAB_ARGS to "19759",
                Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = NomeColabChaveViewModel(
        savedStateHandle,
        getNomeColab,
        setMatricColabMovChave
    )

    @Test
    fun `returnNomeColab - Check return failure if have error in GetNomeColab`() =
        runTest {
            whenever(
                getNomeColab("19759")
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetNomeColab",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.returnNomeColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetNomeColab -> java.lang.Exception"
            )
        }

    @Test
    fun `returnNomeColab - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getNomeColab("19759")
            ).thenReturn(
                Result.success("ANDERSON DA SILVA DELGADO")
            )
            val viewModel = getViewModel()
            viewModel.returnNomeColab()
            assertEquals(
                viewModel.uiState.value.nomeColab,
                "ANDERSON DA SILVA DELGADO"
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in SetMatricColab`() =
        runTest {
            whenever(
                setMatricColabMovChave(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "ISetMatricColabMovChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.ID_ARGS to 0
                    )
                )
            )
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> ISetMatricColabMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                setMatricColabMovChave(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.ID_ARGS to 0
                    )
                )
            )
            viewModel.setMatricColab()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}