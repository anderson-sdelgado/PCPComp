package br.com.usinasantafe.pcpcomp.presenter.nomevigia

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.CheckAccessMain
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.RecoverNomeVigia
import br.com.usinasantafe.pcpcomp.presenter.menuinicial.MenuInicialViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NomeVigiaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `check return failure if RecoverNomeVigia have failure`() = runTest {
        val recoverNomeVigia = mock<RecoverNomeVigia>()
        whenever(recoverNomeVigia()).thenReturn(
            Result.failure(
                DatasourceException(cause = Exception())
            )
        )
        val viewModel = NomeVigiaViewModel(recoverNomeVigia)
        viewModel.returnNomeVigia()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Error RecoverNomeVigia -> Failure Datasource -> java.lang.Exception")
    }

    @Test
    fun `check return name if RecoverNomeVigia is success`() = runTest {
        val recoverNomeVigia = mock<RecoverNomeVigia>()
        whenever(recoverNomeVigia()).thenReturn(
            Result.success("ANDERSON DA SILVA DELGADO")
        )
        val viewModel = NomeVigiaViewModel(recoverNomeVigia)
        viewModel.returnNomeVigia()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.nomeVigia, "ANDERSON DA SILVA DELGADO")
    }
}