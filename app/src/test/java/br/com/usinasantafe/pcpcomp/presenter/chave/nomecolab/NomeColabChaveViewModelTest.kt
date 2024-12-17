package br.com.usinasantafe.pcpcomp.presenter.chave.nomecolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SetMatricColabMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.StartReceiptMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetNomeColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
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
    private val startReceiptMovChave = mock<StartReceiptMovChave>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.MATRIC_COLAB_ARGS to "19759",
                Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                Args.TYPE_MOV_ARGS to TypeMovKey.REMOVE.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = NomeColabChaveViewModel(
        savedStateHandle,
        getNomeColab,
        setMatricColabMovChave,
        startReceiptMovChave
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
    fun `setMatricColab - Check return failure if have error in SetMatricColab - REMOVE - ADD`() =
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
            val viewModel = getViewModel()
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
    fun `setMatricColab - Check return correct if function execute successfully - REMOVE - ADD`() =
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
            val viewModel = getViewModel()
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

    @Test
    fun `setMatricColab - Check return failure if have error in StartReceiptChave - RECEIPT - ADD`() =
        runTest {
            whenever(
                startReceiptMovChave(
                    1
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "StartReceiptChaveImpl",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
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
                "Failure Usecase -> StartReceiptChaveImpl -> java.lang.Exception"
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in SetMatricColab - RECEIPT - ADD`() =
        runTest {
            whenever(
                startReceiptMovChave(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setMatricColabMovChave(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 1
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
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
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
    fun `setMatricColab - Check return correct if function execute successfully - RECEIPT - ADD`() =
        runTest {
            whenever(
                startReceiptMovChave(
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setMatricColabMovChave(
                    matricColab = "19759",
                    flowApp = FlowApp.ADD,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        Args.MATRIC_COLAB_ARGS to "19759",
                        Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                        Args.TYPE_MOV_ARGS  to TypeMovKey.RECEIPT.ordinal,
                        Args.ID_ARGS to 1
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