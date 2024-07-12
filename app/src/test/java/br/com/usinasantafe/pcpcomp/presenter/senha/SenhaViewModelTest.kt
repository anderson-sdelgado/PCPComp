package br.com.usinasantafe.pcpcomp.presenter.senha

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.usecases.CheckPasswordConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.CheckPasswordConfigImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.inject
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SenhaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val password = "12345"

    @Test
    fun `check password true`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(checkPasswordConfig(password)).thenReturn(true)
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertTrue(viewModel.uiState.value.statusDialog)
    }

    @Test
    fun `check password false`() = runTest {
        val checkPasswordConfig = mock<CheckPasswordConfig>()
        whenever(checkPasswordConfig(password)).thenReturn(false)
        val viewModel = SenhaViewModel(checkPasswordConfig)
        viewModel.updatePassword(password)
        viewModel.checkPassword()
        assertFalse(viewModel.uiState.value.statusDialog)
    }

}