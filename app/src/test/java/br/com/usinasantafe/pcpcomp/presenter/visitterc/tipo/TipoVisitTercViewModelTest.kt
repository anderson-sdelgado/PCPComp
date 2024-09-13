package br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetTipoVisitTerc
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TipoVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error on set type visit terc`() = runTest {
        val setTipoVisitTerc = mock<SetTipoVisitTerc>()
        whenever(setTipoVisitTerc(TypeVisitTerc.TERCEIRO)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetTipoVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = TipoVisitTercViewModel(setTipoVisitTerc)
        viewModel.setTypeVisitTerc(TypeVisitTerc.TERCEIRO)
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> SetTipoVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return true if set type visit terc execute success`() = runTest {
        val setTipoVisitTerc = mock<SetTipoVisitTerc>()
        whenever(setTipoVisitTerc(TypeVisitTerc.TERCEIRO)).thenReturn(
            Result.success(true)
        )
        val viewModel = TipoVisitTercViewModel(setTipoVisitTerc)
        viewModel.setTypeVisitTerc(TypeVisitTerc.TERCEIRO)
        assertTrue(viewModel.uiState.value.flagAccess)
    }

}