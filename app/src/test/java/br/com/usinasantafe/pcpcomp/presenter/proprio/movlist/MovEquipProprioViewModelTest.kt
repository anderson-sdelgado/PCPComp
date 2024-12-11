package br.com.usinasantafe.pcpcomp.presenter.proprio.movlist

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CloseAllMovProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetMovEquipProprioOpenList
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.StartMovEquipProprio
import br.com.usinasantafe.pcpcomp.presenter.model.HeaderModel
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
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
        val closeAllMovProprio = mock<CloseAllMovProprio>()
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
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.returnHeader()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return nome Vigia if recoverHeader execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
        whenever(getHeader()).thenReturn(
            Result.success(
                HeaderModel(
                    descrVigia = "19759 - Anderson da Silva Delgado",
                    descrLocal = "1 - Usina"
                )
            )
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.returnHeader()
        assertEquals(viewModel.uiState.value.descrVigia, "19759 - Anderson da Silva Delgado")
        assertEquals(viewModel.uiState.value.descrLocal, "1 - Usina")
    }

    @Test
    fun `Check return failure if startMovEquipProprio have failure`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
        whenever(startMovEquipProprio(typeMov = TypeMovEquip.INPUT)).thenReturn(
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
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.startMov(typeMov = TypeMovEquip.INPUT)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> StartMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return success if startMovEquipProprio execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
        whenever(startMovEquipProprio(typeMov = TypeMovEquip.INPUT)).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.startMov(typeMov = TypeMovEquip.INPUT)
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

    @Test
    fun `Check return failure if RecoverMovEquipProprioOpen have failure`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
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
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> RecoverMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return emptyList if not have mov open`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
        whenever(getMovEquipProprioOpenList()).thenReturn(
            Result.success(emptyList())
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList,
            closeAllMovProprio
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
        val closeAllMovProprio = mock<CloseAllMovProprio>()
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
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.recoverMovEquipOpenList()
        assertEquals(viewModel.uiState.value.movEquipProprioModelList.size, 1)
        assertEquals(viewModel.uiState.value.movEquipProprioModelList[0], movEquipProprioViewModel)
    }

    @Test
    fun `Check return failure if closeAllMovProprioOpen have failure`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
        whenever(closeAllMovProprio()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CloseAllMovProprioOpen",
                    cause = Exception()
                )
            )
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.closeAllMov()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CloseAllMovProprioOpen -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if closeAllMovProprioOpen execute correctly`() = runTest {
        val getHeader = mock<GetHeader>()
        val startMovEquipProprio = mock<StartMovEquipProprio>()
        val getMovEquipProprioOpenList = mock<GetMovEquipProprioOpenList>()
        val closeAllMovProprio = mock<CloseAllMovProprio>()
        whenever(closeAllMovProprio()).thenReturn(
            Result.success(true)
        )
        val viewModel = MovEquipProprioListViewModel(
            getHeader,
            startMovEquipProprio,
            getMovEquipProprioOpenList,
            closeAllMovProprio
        )
        viewModel.closeAllMov()
        assertEquals(viewModel.uiState.value.flagCloseAllMov, true)
    }
}