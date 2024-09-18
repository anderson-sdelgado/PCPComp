package br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.CloseAllMovResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetMovEquipResidenciaOpenList
import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipResidenciaEditListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in GetMovEquipResidenciaOpenList`() = runTest {
        val getMovEquipResidenciaOpenList = mock<GetMovEquipResidenciaOpenList>()
        val closeAllMovResidencia = mock<CloseAllMovResidencia>()
        whenever(getMovEquipResidenciaOpenList()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetMovEquipResidenciaOpenList",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipResidenciaEditListViewModel(
            getMovEquipResidenciaOpenList,
            closeAllMovResidencia
        )
        viewModel.recoverMovEquipEditList()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> GetMovEquipResidenciaOpenList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if GetMovEquipResidenciaOpenList execute correctly`() = runTest {
        val list = listOf(
            MovEquipResidenciaModel(
                id = 1,
                dthr = "08/08/2024 12:00",
                motorista = "Anderson da Silva Delgado",
                veiculo = "GOL",
                placa = "ABC-1234"
            )
        )
        val getMovEquipResidenciaOpenList = mock<GetMovEquipResidenciaOpenList>()
        val closeAllMovResidencia = mock<CloseAllMovResidencia>()
        whenever(getMovEquipResidenciaOpenList()).thenReturn(
            Result.success(list)
        )
        val viewModel = MovEquipResidenciaEditListViewModel(
            getMovEquipResidenciaOpenList,
            closeAllMovResidencia
        )
        viewModel.recoverMovEquipEditList()
        val listMov = viewModel.uiState.value.movEquipResidenciaModelList
        assertEquals(listMov.size, 1)
        assertEquals(listMov[0].veiculo, "GOL")
    }

    @Test
    fun `Check return failure if have error in CloseAllMovResidenciaOpen`() = runTest {
        val getMovEquipResidenciaOpenList = mock<GetMovEquipResidenciaOpenList>()
        val closeAllMovResidencia = mock<CloseAllMovResidencia>()
        whenever(closeAllMovResidencia()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CloseAllMovResidenciaOpen",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipResidenciaEditListViewModel(
            getMovEquipResidenciaOpenList,
            closeAllMovResidencia
        )
        viewModel.closeAllMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> CloseAllMovResidenciaOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if CloseAllMovResidenciaOpen execute correctly`() = runTest {
        val getMovEquipResidenciaOpenList = mock<GetMovEquipResidenciaOpenList>()
        val closeAllMovResidencia = mock<CloseAllMovResidencia>()
        whenever(closeAllMovResidencia()).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipResidenciaEditListViewModel(
            getMovEquipResidenciaOpenList,
            closeAllMovResidencia
        )
        viewModel.closeAllMov()
        val state = viewModel.uiState.value
        assertTrue(state.flagCloseAllMov)
    }
}