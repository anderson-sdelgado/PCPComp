package br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.CheckAccessMain
import br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial.MenuInicialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MenuInicialViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check return failure if checkAccess have failure`() = runTest {
        val checkAccessMain = mock<CheckAccessMain>()
        whenever(checkAccessMain()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferences.hasConfig",
                    cause = Exception()
                )
            )
        )
        val viewModel = MenuInicialViewModel(checkAccessMain)
        viewModel.checkAccess()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception")
    }

    @Test
    fun `check blocked access`() = runTest {
        val checkAccessMain = mock<CheckAccessMain>()
        whenever(checkAccessMain()).thenReturn(
            Result.success(false)
        )
        val viewModel = MenuInicialViewModel(checkAccessMain)
        viewModel.checkAccess()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
        assertEquals(viewModel.uiState.value.flagAccess, false)
    }

    @Test
    fun `check access granted`() = runTest {
        val checkAccessMain = mock<CheckAccessMain>()
        whenever(checkAccessMain()).thenReturn(
            Result.success(true)
        )
        val viewModel = MenuInicialViewModel(checkAccessMain)
        viewModel.checkAccess()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagFailure, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

}