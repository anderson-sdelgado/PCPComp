package br.com.usinasantafe.pcpcomp.presenter.chave.matriccolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class MatricColabChaveViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateColab = mock<UpdateColab>()
    private val checkMatricColab = mock<CheckMatricColab>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = MatricColabChaveViewModel(
        savedStateHandle,
        updateColab,
        checkMatricColab
    )

    @Test
    fun `Check add numeric with click in buttons`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.matricColab,
                "19759"
            )
        }

    @Test
    fun `Check clean if click in button clear`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.CLEAN)
            viewModel.setTextField("", TypeButton.CLEAN)
            assertEquals(
                viewModel.uiState.value.matricColab,
                "197"
            )
        }

    @Test
    fun `Check return failure if click in button ok and matricColab is empty`() =
        runTest {
            val viewModel = getViewModel()
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELDEMPTY
            )
        }

    @Test
    fun `Check return failure usecase in setTextField if have error in usecase UpdateColab`() =
        runTest {
            whenever(
                updateColab(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = getViewModel()
            val result = viewModel.updateAllDatabase().toList()
            Assert.assertEquals(result.count(), 2)
            Assert.assertEquals(
                result[0],
                MatricColabChaveState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            Assert.assertEquals(
                result[1],
                MatricColabChaveState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
            Assert.assertEquals(
                viewModel.uiState.value.msgProgress,
                "Failure Usecase -> CleanColab -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return success in updateAllDatabase if all update run correctly`() =
        runTest {
            whenever(
                updateColab(
                    count = 1f,
                    sizeAll = 4f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_colab",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val viewModel = getViewModel()
            val result = viewModel.updateAllDatabase().toList()
            Assert.assertEquals(result.count(), 4)
            Assert.assertEquals(
                result[0],
                MatricColabChaveState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            Assert.assertEquals(
                result[1],
                MatricColabChaveState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                    currentProgress = percentage(2f, 4f),
                )
            )
            Assert.assertEquals(
                result[2],
                MatricColabChaveState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_colab",
                    currentProgress = percentage(3f, 4f),
                )
            )
            Assert.assertEquals(
                result[3],
                MatricColabChaveState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
            Assert.assertEquals(viewModel.uiState.value.flagDialog, true)
            Assert.assertEquals(
                viewModel.uiState.value.msgProgress,
                "Atualização de dados realizado com sucesso!"
            )
        }

    @Test
    fun `setMatricColab - Check return failure if have error in CheckMatricColab`() =
        runTest {
            whenever(
                checkMatricColab("19759")
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CheckMatricColab",
                        cause = NullPointerException()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> CheckMatricColab -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `setMatricColab - Check return false if function execute successfully but access is denied`() =
        runTest {
            whenever(
                checkMatricColab("19759")
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setMatricColab - Check return true if function execute successfully and free access`() =
        runTest {
            whenever(
                checkMatricColab("19759")
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
        }
}