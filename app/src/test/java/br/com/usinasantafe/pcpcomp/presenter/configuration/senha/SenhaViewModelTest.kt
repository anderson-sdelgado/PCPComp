package br.com.usinasantafe.pcpcomp.presenter.configuration.senha

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.usecases.config.CheckPasswordConfig
import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.SenhaViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SenhaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val password = "12345"

    private fun getViewModel() = SenhaViewModel(
        checkPasswordConfig = mock()
    )

    @Test
    fun `check return failure if checkPassword have failure`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(
            checkPasswordConfig(password)
        ).thenReturn(
            Result.failure(
                DatasourceException(function = "ConfigSharedPreferences.hasConfig", cause = Exception())
            )
        )
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception")
    }

    @Test
    fun `check blocked access`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(checkPasswordConfig(password)).thenReturn(
            Result.success(false)
        )
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
        assertEquals(viewModel.uiState.value.flagAccess, false)
    }

    @Test
    fun `check access granted`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(checkPasswordConfig(password)).thenReturn(
            Result.success(true)
        )
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagFailure, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

}