package br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CleanEquipSeg
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.DeleteEquipSeg
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetEquipSegList
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class EquipSegListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if CleanEquipSeg have failure`() = runTest {
        val cleanEquipSeg = mock<CleanEquipSeg>()
        val getEquipSegList = mock<GetEquipSegList>()
        val deleteEquipSeg = mock<DeleteEquipSeg>()
        whenever(cleanEquipSeg()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanEquipSeg",
                    cause = Exception()
                )
            )
        )
        val viewModel = EquipSegListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanEquipSeg,
            getEquipSegList,
            deleteEquipSeg
        )
        viewModel.cleanVeicSeg()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CleanEquipSeg -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have failure in RecoverEquipSeg`() = runTest {
        val cleanEquipSeg = mock<CleanEquipSeg>()
        val getEquipSegList = mock<GetEquipSegList>()
        val deleteEquipSeg = mock<DeleteEquipSeg>()
        whenever(
            getEquipSegList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverEquipSeg",
                    cause = Exception()
                )
            )
        )
        val viewModel = EquipSegListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanEquipSeg,
            getEquipSegList,
            deleteEquipSeg
        )
        viewModel.recoverVeicSeg()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> RecoverEquipSeg -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Equip if RecoverEquipSeg execute successfully`() = runTest {
        val cleanEquipSeg = mock<CleanEquipSeg>()
        val getEquipSegList = mock<GetEquipSegList>()
        val deleteEquipSeg = mock<DeleteEquipSeg>()
        whenever(
            getEquipSegList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200
                    )
                )
            )
        )
        val viewModel = EquipSegListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanEquipSeg,
            getEquipSegList,
            deleteEquipSeg
        )
        viewModel.recoverVeicSeg()
        assertEquals(viewModel.uiState.value.equipSegList.size, 2)
        assertEquals(viewModel.uiState.value.equipSegList[0].idEquip, 10)
    }

    @Test
    fun `Check return failure if have failure in DeleteEquipSeg`() = runTest {
        val cleanEquipSeg = mock<CleanEquipSeg>()
        val getEquipSegList = mock<GetEquipSegList>()
        val deleteEquipSeg = mock<DeleteEquipSeg>()
        whenever(
            deleteEquipSeg(
                10,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "DeleteEquipSeg",
                    cause = Exception()
                )
            )
        )
        val viewModel = EquipSegListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanEquipSeg,
            getEquipSegList,
            deleteEquipSeg
        )
        viewModel.setDelete(10)
        assertEquals(viewModel.uiState.value.flagDialogCheck, true)
        viewModel.deleteVeicSeg()
        assertEquals(viewModel.uiState.value.flagDialogCheck, false)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> DeleteEquipSeg -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab after deleteEquipSeg execute successfully`() = runTest {
        val cleanEquipSeg = mock<CleanEquipSeg>()
        val getEquipSegList = mock<GetEquipSegList>()
        val deleteEquipSeg = mock<DeleteEquipSeg>()
        whenever(
            deleteEquipSeg(
                10,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getEquipSegList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Equip(
                        idEquip = 10,
                        nroEquip = 100
                    ),
                    Equip(
                        idEquip = 20,
                        nroEquip = 200
                    )
                )
            )
        )
        val viewModel = EquipSegListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanEquipSeg,
            getEquipSegList,
            deleteEquipSeg
        )
        viewModel.setDelete(10)
        assertEquals(viewModel.uiState.value.flagDialogCheck, true)
        viewModel.deleteVeicSeg()
        assertEquals(viewModel.uiState.value.flagDialogCheck, false)
        assertEquals(viewModel.uiState.value.equipSegList.size, 2)
        assertEquals(viewModel.uiState.value.equipSegList[0].idEquip, 10)
    }

}