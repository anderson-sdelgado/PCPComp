package br.com.usinasantafe.pcpcomp.presenter.senha

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.usecases.config.CheckPasswordConfig
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

    @Test
    fun `check access granted`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(checkPasswordConfig(password)).thenReturn(Result.success(true))
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertEquals(viewModel.uiState.value.flagDialog, false)
    }

    @Test
    fun `check blocked access`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(checkPasswordConfig(password)).thenReturn(Result.success(false))
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertEquals(viewModel.uiState.value.flagDialog, true)
    }

}