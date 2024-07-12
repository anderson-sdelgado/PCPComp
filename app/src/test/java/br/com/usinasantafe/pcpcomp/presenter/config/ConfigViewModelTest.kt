package br.com.usinasantafe.pcpcomp.presenter.config

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.usecases.CheckPasswordConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.RecoverConfig
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ConfigViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check return null if don't have Config table internal`() = runTest {
        val recoverConfig = mock<RecoverConfig>()
        whenever(recoverConfig()).thenReturn(null)
        val viewModel = ConfigViewModel(recoverConfig)
        viewModel.returnDataConfig()
        assertEquals(viewModel.uiState.value.number, "")
        assertEquals(viewModel.uiState.value.password, "")
    }


    @Test
    fun `check return data if have Config table internal`() = runTest {
        val configModel = ConfigModel(
            number = "16997417840",
            password = "12345"
        )
        val recoverConfig = mock<RecoverConfig>()
        whenever(recoverConfig()).thenReturn(configModel)
        val viewModel = ConfigViewModel(recoverConfig)
        viewModel.returnDataConfig()
        assertEquals(viewModel.uiState.value.number, "16997417840")
        assertEquals(viewModel.uiState.value.password, "12345")
    }
}