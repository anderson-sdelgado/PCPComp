package br.com.usinasantafe.pcpcomp.presenter.visitterc.moveditlist

import GetMovEquipVisitTercOpenList
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CloseAllMovVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.visitterc.model.MovEquipVisitTercModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipVisitTercEditListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in GetMovEquipVisitTercOpenList`() = runTest {
        val getMovEquipVisitTercOpenList = mock<GetMovEquipVisitTercOpenList>()
        val closeAllMovVisitTerc = mock<CloseAllMovVisitTerc>()
        whenever(getMovEquipVisitTercOpenList()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetMovEquipVisitTercOpenList",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipVisitTercEditListViewModel(
            getMovEquipVisitTercOpenList,
            closeAllMovVisitTerc
        )
        viewModel.recoverMovEquipEditList()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> GetMovEquipVisitTercOpenList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if GetMovEquipVisitTercOpenList execute correctly`() = runTest {
        val list = listOf(
            MovEquipVisitTercModel(
                id = 1,
                dthr = "08/08/2024 12:00",
                motorista = "Anderson da Silva Delgado",
                veiculo = "GOL",
                placa = "ABC-1234",
                tipoVisitTerc = "VISITANTE"
            )
        )
        val getMovEquipVisitTercOpenList = mock<GetMovEquipVisitTercOpenList>()
        val closeAllMovVisitTerc = mock<CloseAllMovVisitTerc>()
        whenever(getMovEquipVisitTercOpenList()).thenReturn(
            Result.success(list)
        )
        val viewModel = MovEquipVisitTercEditListViewModel(
            getMovEquipVisitTercOpenList,
            closeAllMovVisitTerc
        )
        viewModel.recoverMovEquipEditList()
        val listMov = viewModel.uiState.value.movEquipVisitTercModelList
        assertEquals(listMov.size, 1)
        assertEquals(listMov[0].veiculo, "GOL")
    }

    @Test
    fun `Check return failure if have error in CloseAllMovVisitTercOpen`() = runTest {
        val getMovEquipVisitTercOpenList = mock<GetMovEquipVisitTercOpenList>()
        val closeAllMovVisitTerc = mock<CloseAllMovVisitTerc>()
        whenever(closeAllMovVisitTerc()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CloseAllMovVisitTercOpen",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipVisitTercEditListViewModel(
            getMovEquipVisitTercOpenList,
            closeAllMovVisitTerc
        )
        viewModel.closeAllMov()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CloseAllMovVisitTercOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseAllMovVisitTercOpen execute correctly`() = runTest {
        val getMovEquipVisitTercOpenList = mock<GetMovEquipVisitTercOpenList>()
        val closeAllMovVisitTerc = mock<CloseAllMovVisitTerc>()
        whenever(closeAllMovVisitTerc()).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipVisitTercEditListViewModel(
            getMovEquipVisitTercOpenList,
            closeAllMovVisitTerc
        )
        viewModel.closeAllMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagCloseAllMov)
    }
}