package br.com.usinasantafe.pcpcomp.presenter.residencia.movlist

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetMovEquipResidenciaInsideList
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.StartInputMovEquipResidencia
import br.com.usinasantafe.pcpcomp.presenter.model.HeaderModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipResidenciaListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
        val getHeader = mock<GetHeader>()
        val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
        val startINputMovEquipResidencia = mock<StartInputMovEquipResidencia>()
        whenever(getHeader()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferences.hasConfig",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipResidenciaListViewModel(
            getHeader,
            getMovEquipResidenciaInsideList,
            startINputMovEquipResidencia
        )
        viewModel.returnHeader()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return Nome Vigia if getHeader execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
        val startINputMovEquipResidencia = mock<StartInputMovEquipResidencia>()
        whenever(getHeader()).thenReturn(
            Result.success(
                HeaderModel(
                    descrVigia = "19759 - Anderson da Silva Delgado",
                    descrLocal = "1 - Usina"
                )
            )
        )
        val viewModel = MovEquipResidenciaListViewModel(
            getHeader,
            getMovEquipResidenciaInsideList,
            startINputMovEquipResidencia
        )
        viewModel.returnHeader()
        assertEquals(viewModel.uiState.value.descrVigia, "19759 - Anderson da Silva Delgado")
        assertEquals(
            viewModel.uiState.value.descrLocal,
            "1 - Usina"
        )
    }

    @Test
    fun `Check return failure if have error in recoverMovEquipInputList`() = runTest {
        val getHeader = mock<GetHeader>()
        val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
        val startINputMovEquipResidencia = mock<StartInputMovEquipResidencia>()
        whenever(getMovEquipResidenciaInsideList()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetMovEquipResidenciaInputOpenList",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipResidenciaListViewModel(
            getHeader,
            getMovEquipResidenciaInsideList,
            startINputMovEquipResidencia
        )
        viewModel.recoverMovEquipList()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> GetMovEquipResidenciaInputOpenList -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list if recoverMovEquipInputList execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
        val startINputMovEquipResidencia = mock<StartInputMovEquipResidencia>()
        whenever(getMovEquipResidenciaInsideList()).thenReturn(
            Result.success(
                listOf(
                    MovEquipResidenciaModel(
                        id = 1,
                        dthr = "DATA/HORA: 08/08/2024 12:00",
                        motorista = "MOTORISTA: ANDERSON DA SILVA DELGADO",
                        veiculo = "VEÍCULO: GOL",
                        placa = "PLACA: ABC1234",
                    )
                )
            )
        )
        val viewModel = MovEquipResidenciaListViewModel(
            getHeader,
            getMovEquipResidenciaInsideList,
            startINputMovEquipResidencia
        )
        viewModel.recoverMovEquipList()
        val state = viewModel.uiState.value
        assertEquals(state.movEquipResidenciaModelList.size, 1)
        assertEquals(
            state.movEquipResidenciaModelList[0].dthr,
            "DATA/HORA: 08/08/2024 12:00"
        )
    }

    @Test
    fun `Check return failure if have error in startMovEquipResidencia`() = runTest {
        val getHeader = mock<GetHeader>()
        val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
        val startINputMovEquipResidencia = mock<StartInputMovEquipResidencia>()
        whenever(startINputMovEquipResidencia()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "StartMovEquipResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipResidenciaListViewModel(
            getHeader,
            getMovEquipResidenciaInsideList,
            startINputMovEquipResidencia
        )
        viewModel.startMov()
        assertTrue(viewModel.uiState.value.flagDialog)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> StartMovEquipResidencia -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if startMovEquipResidencia execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val getMovEquipResidenciaInsideList = mock<GetMovEquipResidenciaInsideList>()
        val startINputMovEquipResidencia = mock<StartInputMovEquipResidencia>()
        whenever(startINputMovEquipResidencia()).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipResidenciaListViewModel(
            getHeader,
            getMovEquipResidenciaInsideList,
            startINputMovEquipResidencia
        )
        viewModel.startMov()
        assertTrue(viewModel.uiState.value.flagAccess)
        assertFalse(viewModel.uiState.value.flagDialog)
    }
}