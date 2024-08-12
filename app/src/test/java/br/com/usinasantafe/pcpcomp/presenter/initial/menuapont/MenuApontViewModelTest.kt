package br.com.usinasantafe.pcpcomp.presenter.initial.menuapont

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.RecoverHeader
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CloseAllMovOpen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MenuApontViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check return failure if recoverHeader have failure`() = runTest {
        val recoverHeader = mock<RecoverHeader>()
        val closeAllMovOpen = mock<CloseAllMovOpen>()
        whenever(recoverHeader()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferences.hasConfig",
                    cause = Exception()
                )
            )
        )
        val viewModel = MenuApontViewModel(
            recoverHeader,
            closeAllMovOpen
        )
        viewModel.returnHeader()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception")
    }

    @Test
    fun `check return failure if closeAllMovOpen have failure`() = runTest {
        val recoverHeader = mock<RecoverHeader>()
        val closeAllMovOpen = mock<CloseAllMovOpen>()
        whenever(closeAllMovOpen()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CloseAllMovOpen",
                    cause = Exception()
                )
            )
        )
        val viewModel = MenuApontViewModel(
            recoverHeader,
            closeAllMovOpen
        )
        viewModel.closeAllMov()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> CloseAllMovOpen -> java.lang.Exception")
    }

    @Test
    fun `check return Splash if closeAllMovOpen execute correctly`() = runTest {
        val recoverHeader = mock<RecoverHeader>()
        val closeAllMovOpen = mock<CloseAllMovOpen>()
        whenever(closeAllMovOpen()).thenReturn(
            Result.success(true)
        )
        val viewModel = MenuApontViewModel(
            recoverHeader,
            closeAllMovOpen
        )
        viewModel.closeAllMov()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagReturn, true)
    }
}