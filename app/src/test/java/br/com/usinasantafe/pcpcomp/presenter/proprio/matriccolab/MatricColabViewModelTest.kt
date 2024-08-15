package br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver.RecoverColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.porc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MatricColabViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check add char in matricMotorista`() {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        assertEquals(viewModel.uiState.value.matricColab, "19759")
    }

    @Test
    fun `Check remover and add char in matricMotorista`() {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("1", TypeButton.NUMERIC)
        assertEquals(viewModel.uiState.value.matricColab, "191")
    }

    @Test
    fun `Check view msg if field is empty`() {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.FIELDEMPTY)
    }

    @Test
    fun `Check return failure if have error in CheckMatricColab`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "CheckMatricColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.EXCEPTION)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> CheckMatricColab -> java.lang.Exception")
    }

    @Test
    fun `Check return false if matric is invalid`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.success(true)
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
    }

    @Test
    fun `Check return true if matric is valid`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.success(false)
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.flagFailure, true)
    }

    @Test
    fun `check return failure datasource if have error in usecase CleanColab is datasource`() =
        runTest {
            val checkMatricColab = mock<CheckMatricColab>()
            val cleanColab = mock<CleanColab>()
            val recoverColabServer = mock<RecoverColabServer>()
            val saveAllColab = mock<SaveAllColab>()
            whenever(
                cleanColab()
            ).thenReturn(
                Result.failure(
                    DatasourceException(
                        function = "CleanColab",
                        cause = NullPointerException()
                    )
                )
            )
            val viewModel = MatricColabViewModel(
                SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
                checkMatricColab,
                cleanColab,
                recoverColabServer,
                saveAllColab
            )
            val result = viewModel.updateAllColab(count = 1f, sizeAll = 4f).toList()
            assertEquals(result.count(), 2)
            assertEquals(
                result[0],
                MatricColabState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_colab",
                    currentProgress = porc(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                MatricColabState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Datasource -> CleanColab -> java.lang.NullPointerException",
                    msgProgress = "Failure Datasource -> CleanColab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `check return failure datasource if have error in datasource RecoverAllColab`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "RecoverColabServer",
                    cause = Exception()
                )
            )
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        val result = viewModel.updateAllColab(count = 1f, sizeAll = 4f).toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f, 4f),
            )
        )
        assertEquals(
            result[2],
            MatricColabState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> RecoverColabServer -> java.lang.Exception",
                msgProgress = "Failure Datasource -> RecoverColabServer -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure datasource if have error in datasource SaveAllColab`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.success(colabList)
        )
        whenever(
            saveAllColab(colabList)
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "SaveAllColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        val result = viewModel.updateAllColab(count = 1f, sizeAll = 4f).toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f, 4f),
            )
        )
        assertEquals(
            result[2],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = porc(3f, 4f),
            )
        )
        assertEquals(
            result[3],
            MatricColabState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Datasource -> SaveAllColab -> java.lang.Exception",
                msgProgress = "Failure Datasource -> SaveAllColab -> java.lang.Exception",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure usecase execute updateAllDatabase if have error in usecase RecoverColabServer`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverColabServer",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 3)
        assertEquals(
            result[0],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f , 4f),
            )
        )
        assertEquals(
            result[2],
            MatricColabState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return failure usecase in setTextField if have error in usecase CleanColab`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        whenever(
            cleanColab()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanColab",
                    cause = NullPointerException()
                )
            )
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab,
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 2)
        assertEquals(
            result[0],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            MatricColabState(
                errors = Errors.UPDATE,
                flagDialog = true,
                flagFailure = true,
                failure = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                msgProgress = "Failure Usecase -> CleanColab -> java.lang.NullPointerException",
                currentProgress = 1f,
            )
        )
        viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
        assertEquals(
            viewModel.uiState.value.msgProgress,
            "Failure Usecase -> CleanColab -> java.lang.NullPointerException"
        )
    }

    @Test
    fun `check return success in updateAllDatabase if all update run correctly`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.success(colabList)
        )
        whenever(
            saveAllColab(colabList)
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        val result = viewModel.updateAllDatabase().toList()
        assertEquals(result.count(), 4)
        assertEquals(
            result[0],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Limpando a tabela tb_colab",
                currentProgress = porc(1f, 4f)
            )
        )
        assertEquals(
            result[1],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela tb_colab do Web Service",
                currentProgress = porc(2f, 4f),
            )
        )
        assertEquals(
            result[2],
            MatricColabState(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela tb_colab",
                currentProgress = porc(3f, 4f),
            )
        )
        assertEquals(
            result[3],
            MatricColabState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                msgProgress = "Atualização de dados realizado com sucesso!",
                currentProgress = 1f,
            )
        )
    }

    @Test
    fun `check return success if setTextField update is success`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val cleanColab = mock<CleanColab>()
        val recoverColabServer = mock<RecoverColabServer>()
        val saveAllColab = mock<SaveAllColab>()
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        whenever(
            cleanColab()
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            recoverColabServer()
        ).thenReturn(
            Result.success(colabList)
        )
        whenever(
            saveAllColab(colabList)
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = MatricColabViewModel(
            SavedStateHandle(mapOf(Args.FLOW_APP_ARGS to 0)),
            checkMatricColab,
            cleanColab,
            recoverColabServer,
            saveAllColab
        )
        viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.msgProgress, "Atualização de dados realizado com sucesso!")
    }

}