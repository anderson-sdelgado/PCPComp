package br.com.usinasantafe.pcpcomp.presenter.proprio.movlist

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetMovEquipProprioOpenList
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.StartMovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovEquipProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if recoverHeader have failure`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        whenever(getHeader()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferences.hasConfig",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList
        )
        viewModel.returnHeader()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if startMovEquipProprio have failure`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        whenever(startMovEquipProprio(typeMov = TypeMov.INPUT)).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "StartMovEquipProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList
        )
        viewModel.startMov(typeMov = TypeMov.INPUT)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> StartMovEquipProprio -> java.lang.Exception")
    }

    @Test
    fun `Check return success if startMovEquipProprio execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        whenever(startMovEquipProprio(typeMov = TypeMov.INPUT)).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList
        )
        viewModel.startMov(typeMov = TypeMov.INPUT)
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

    @Test
    fun `Check return failure if RecoverMovEquipProprioOpen have failure`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        whenever(getMovEquipProprioOpenList()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverMovEquipProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Usecase -> RecoverMovEquipProprio -> java.lang.Exception")
    }

    @Test
    fun `Check return emptyList if not have mov open`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        whenever(getMovEquipProprioOpenList()).thenReturn(
            Result.success(emptyList())
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(viewModel.uiState.value.movEquipProprioModelList.isEmpty(), true)
    }

    @Test
    fun `Check return list mov if have mov open`() = runTest {
        val movEquipProprioViewModel = MovEquipProprioModel(
            id = 1,
            dthr = "08/08/2024 12:00",
            typeMov = "ENTRADA",
            equip = "2300",
            colab = "19759 - ANDERSON DA SILVA DELGADO"
        )

        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        whenever(getMovEquipProprioOpenList()).thenReturn(
            Result.success(
                listOf(
                    movEquipProprioViewModel
                )
            )
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(viewModel.uiState.value.movEquipProprioModelList.size, 1)
        assertEquals(viewModel.uiState.value.movEquipProprioModelList[0], movEquipProprioViewModel)
    }
}