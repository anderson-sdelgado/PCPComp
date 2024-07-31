package br.com.usinasantafe.pcpcomp.presenter.matricvigia

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckMatricColab
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MatricVigiaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check add char in matricVigia`() {
        val checkMatricColab = mock<CheckMatricColab>()
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        assertEquals(viewModel.uiState.value.matricVigia, "19759")
    }

    @Test
    fun `Check remover and add char in matricVigia`() {
        val checkMatricColab = mock<CheckMatricColab>()
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("1", TypeButton.NUMERIC)
        assertEquals(viewModel.uiState.value.matricVigia, "191")
    }

    @Test
    fun `Check view msg if field is empty`() {
        val checkMatricColab = mock<CheckMatricColab>()
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.FIELDEMPTY)
    }

    @Test
    fun `Check return failure if have error in CheckMatricColab`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
            )
        )
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.EXCEPTION)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(viewModel.uiState.value.failure, "Error CheckMatricColab -> Failure Datasource -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have error in SetMatricVigiaConfig`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.success(true)
        )
        whenever(setMatricVigiaConfig("19759")).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
            )
        )
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.EXCEPTION)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(viewModel.uiState.value.failure, "Error SetMatricVigiaConfig -> Failure Datasource -> java.lang.Exception")
    }

    @Test
    fun `Check return false if matric is invalid`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.success(true)
        )
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
    }

    @Test
    fun `Check return true if matric is valid`() = runTest {
        val checkMatricColab = mock<CheckMatricColab>()
        whenever(checkMatricColab("19759")).thenReturn(
            Result.success(false)
        )
        val setMatricVigiaConfig = mock<SetMatricVigiaConfig>()
        val viewModel = MatricVigiaViewModel(checkMatricColab, setMatricVigiaConfig)
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.flagFailure, false)
    }
}