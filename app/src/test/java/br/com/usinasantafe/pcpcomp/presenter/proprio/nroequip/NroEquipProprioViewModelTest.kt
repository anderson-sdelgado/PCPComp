package br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.common.CheckNroEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetNroEquipProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetIdEquipProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateEquip
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class NroEquipProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val checkNroEquip = mock<CheckNroEquip>()
    val setIdEquipProprio = mock<SetIdEquipProprio>()
    val updateEquip = mock<UpdateEquip>()
    val getNroEquipProprio = mock<GetNroEquipProprio>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle
    ) = NroEquipProprioViewModel(
        savedStateHandle,
        checkNroEquip,
        setIdEquipProprio,
        updateEquip,
        getNroEquipProprio
    )

    @Test
    fun `Check add char in nroEquip`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        assertEquals(viewModel.uiState.value.nroEquip, "19759")
    }

    @Test
    fun `Check remover and add char in nroEquip`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("19759", TypeButton.NUMERIC)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("APAGAR", TypeButton.CLEAN)
        viewModel.setTextField("1", TypeButton.NUMERIC)
        assertEquals(viewModel.uiState.value.nroEquip, "191")
    }

    @Test
    fun `Check view msg if field is empty`() {
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.FIELDEMPTY)
    }

    @Test
    fun `Check return failure if have error in CheckMatricEquip`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CheckEquipProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("100", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.EXCEPTION)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CheckEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return false if matric is invalid`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(false)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("100", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.flagAccess, false)
        assertEquals(viewModel.uiState.value.flagFailure, true)
    }

    @Test
    fun `Check return true if nroEquip is valid`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("100", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
    }

    @Test
    fun `Check return failure if have error in SetEquipProprio `() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setIdEquipProprio(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetEquipProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("100", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.errors, Errors.EXCEPTION)
        assertEquals(viewModel.uiState.value.flagFailure, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SetEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if nroEquip is valid and setEquipProprio execute success`() = runTest {
        whenever(
            checkNroEquip("100")
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setIdEquipProprio(
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
                    Args.ID_ARGS to 0
                )
            )
        )
        viewModel.setTextField("100", TypeButton.NUMERIC)
        viewModel.setTextField("OK", TypeButton.OK)
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.flagAccess, true)
        assertEquals(viewModel.uiState.value.flagFailure, false)
    }
//
//    @Test
//    fun `check return failure datasource if have error in usecase CleanEquip is datasource`() =
//        runTest {
//            val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//            val setNroEquip = mock<SetNroEquip>()
//            val cleanEquip = mock<CleanEquip>()
//            val getAllEquipServer = mock<GetAllEquipServer>()
//            val saveAllEquip = mock<SaveAllEquip>()
//            val getNroEquip = mock<GetNroEquip>()
//            whenever(
//                cleanEquip()
//            ).thenReturn(
//                Result.failure(
//                    DatasourceException(
//                        function = "CleanEquip",
//                        cause = NullPointerException()
//                    )
//                )
//            )
//            val viewModel = NroEquipProprioViewModel(
//                SavedStateHandle(
//                    mapOf(
//                        Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                        Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                        Args.ID_ARGS to 0
//                    )
//                ),
//                checkNroEquipProprio,
//                setNroEquip,
//                cleanEquip,
//                getAllEquipServer,
//                saveAllEquip,
//                getNroEquip
//            )
//            val result = viewModel.updateAllEquip(count = 1f, sizeAll = 4f).toList()
//            assertEquals(result.count(), 2)
//            assertEquals(
//                result[0],
//                NroEquipProprioState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_equip",
//                    currentProgress = porc(1f, 4f)
//                )
//            )
//            assertEquals(
//                result[1],
//                NroEquipProprioState(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "Failure Datasource -> CleanEquip -> java.lang.NullPointerException",
//                    msgProgress = "Failure Datasource -> CleanEquip -> java.lang.NullPointerException",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `check return failure datasource if have error in datasource RecoverAllEquip`() = runTest {
//        val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//        val setNroEquip = mock<SetNroEquip>()
//        val cleanEquip = mock<CleanEquip>()
//        val getAllEquipServer = mock<GetAllEquipServer>()
//        val saveAllEquip = mock<SaveAllEquip>()
//        val getNroEquip = mock<GetNroEquip>()
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllEquipServer()
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "RecoverEquipServer",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = NroEquipProprioViewModel(
//            SavedStateHandle(
//                mapOf(
//                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                    Args.ID_ARGS to 0
//                )
//            ),
//            checkNroEquipProprio,
//            setNroEquip,
//            cleanEquip,
//            getAllEquipServer,
//            saveAllEquip,
//            getNroEquip
//        )
//        val result = viewModel.updateAllEquip(count = 1f, sizeAll = 4f).toList()
//        assertEquals(result.count(), 3)
//        assertEquals(
//            result[0],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = porc(1f, 4f)
//            )
//        )
//        assertEquals(
//            result[1],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
//                currentProgress = porc(2f, 4f),
//            )
//        )
//        assertEquals(
//            result[2],
//            NroEquipProprioState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> RecoverEquipServer -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> RecoverEquipServer -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `Check return failure datasource if have error in datasource SaveAllEquip`() = runTest {
//        val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//        val setNroEquip = mock<SetNroEquip>()
//        val cleanEquip = mock<CleanEquip>()
//        val getAllEquipServer = mock<GetAllEquipServer>()
//        val saveAllEquip = mock<SaveAllEquip>()
//        val getNroEquip = mock<GetNroEquip>()
//        val equipList = listOf(
//            Equip(
//                idEquip = 10,
//                nroEquip = 100,
//            )
//        )
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllEquipServer()
//        ).thenReturn(
//            Result.success(equipList)
//        )
//        whenever(
//            saveAllEquip(equipList)
//        ).thenReturn(
//            Result.failure(
//                DatasourceException(
//                    function = "SaveAllEquip",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = NroEquipProprioViewModel(
//            SavedStateHandle(
//                mapOf(
//                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                    Args.ID_ARGS to 0
//                )
//            ),
//            checkNroEquipProprio,
//            setNroEquip,
//            cleanEquip,
//            getAllEquipServer,
//            saveAllEquip,
//            getNroEquip
//        )
//        val result = viewModel.updateAllEquip(count = 1f, sizeAll = 4f).toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = porc(1f, 4f)
//            )
//        )
//        assertEquals(
//            result[1],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
//                currentProgress = porc(2f, 4f),
//            )
//        )
//        assertEquals(
//            result[2],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_equip",
//                currentProgress = porc(3f, 4f),
//            )
//        )
//        assertEquals(
//            result[3],
//            NroEquipProprioState(
//                errors = Errors.UPDATE,
//                flagDialog = true,
//                flagFailure = true,
//                failure = "Failure Datasource -> SaveAllEquip -> java.lang.Exception",
//                msgProgress = "Failure Datasource -> SaveAllEquip -> java.lang.Exception",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `Check return failure usecase execute updateAllDatabase if have error in usecase RecoverEquipServer`() =
//        runTest {
//            val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//            val setNroEquip = mock<SetNroEquip>()
//            val cleanEquip = mock<CleanEquip>()
//            val getAllEquipServer = mock<GetAllEquipServer>()
//            val saveAllEquip = mock<SaveAllEquip>()
//            val getNroEquip = mock<GetNroEquip>()
//            whenever(
//                cleanEquip()
//            ).thenReturn(
//                Result.success(true)
//            )
//            whenever(
//                getAllEquipServer()
//            ).thenReturn(
//                Result.failure(
//                    UsecaseException(
//                        function = "RecoverEquipServer",
//                        cause = NullPointerException()
//                    )
//                )
//            )
//            val viewModel = NroEquipProprioViewModel(
//                SavedStateHandle(
//                    mapOf(
//                        Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                        Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                        Args.ID_ARGS to 0
//                    )
//                ),
//                checkNroEquipProprio,
//                setNroEquip,
//                cleanEquip,
//                getAllEquipServer,
//                saveAllEquip,
//                getNroEquip
//            )
//            val result = viewModel.updateAllDatabase().toList()
//            assertEquals(result.count(), 3)
//            assertEquals(
//                result[0],
//                NroEquipProprioState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_equip",
//                    currentProgress = porc(1f, 4f)
//                )
//            )
//            assertEquals(
//                result[1],
//                NroEquipProprioState(
//                    flagProgress = true,
//                    msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
//                    currentProgress = porc(2f, 4f),
//                )
//            )
//            assertEquals(
//                result[2],
//                NroEquipProprioState(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "Failure Usecase -> RecoverEquipServer -> java.lang.NullPointerException",
//                    msgProgress = "Failure Usecase -> RecoverEquipServer -> java.lang.NullPointerException",
//                    currentProgress = 1f,
//                )
//            )
//        }
//
//    @Test
//    fun `check return failure usecase in setTextField if have error in usecase CleanEquip`() =
//        runTest {
//            val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//            val setNroEquip = mock<SetNroEquip>()
//            val cleanEquip = mock<CleanEquip>()
//            val getAllEquipServer = mock<GetAllEquipServer>()
//            val saveAllEquip = mock<SaveAllEquip>()
//            val getNroEquip = mock<GetNroEquip>()
//            whenever(
//                cleanEquip()
//            ).thenReturn(
//                Result.failure(
//                    UsecaseException(
//                        function = "CleanEquip",
//                        cause = NullPointerException()
//                    )
//                )
//            )
//            val viewModel = NroEquipProprioViewModel(
//                SavedStateHandle(
//                    mapOf(
//                        Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                        Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                        Args.ID_ARGS to 0
//                    )
//                ),
//                checkNroEquipProprio,
//                setNroEquip,
//                cleanEquip,
//                getAllEquipServer,
//                saveAllEquip,
//                getNroEquip
//            )
//            val result = viewModel.updateAllDatabase().toList()
//            assertEquals(result.count(), 2)
//            assertEquals(
//                result[0],
//                NroEquipProprioState(
//                    flagProgress = true,
//                    msgProgress = "Limpando a tabela tb_equip",
//                    currentProgress = porc(1f, 4f)
//                )
//            )
//            assertEquals(
//                result[1],
//                NroEquipProprioState(
//                    errors = Errors.UPDATE,
//                    flagDialog = true,
//                    flagFailure = true,
//                    failure = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
//                    msgProgress = "Failure Usecase -> CleanEquip -> java.lang.NullPointerException",
//                    currentProgress = 1f,
//                )
//            )
//            viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
//            assertEquals(
//                viewModel.uiState.value.msgProgress,
//                "Failure Usecase -> CleanEquip -> java.lang.NullPointerException"
//            )
//        }
//
//    @Test
//    fun `check return success in updateAllDatabase if all update run correctly`() = runTest {
//        val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//        val setNroEquip = mock<SetNroEquip>()
//        val cleanEquip = mock<CleanEquip>()
//        val getAllEquipServer = mock<GetAllEquipServer>()
//        val saveAllEquip = mock<SaveAllEquip>()
//        val getNroEquip = mock<GetNroEquip>()
//        val equipList = listOf(
//            Equip(
//                idEquip = 10,
//                nroEquip = 100,
//            ),
//        )
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllEquipServer()
//        ).thenReturn(
//            Result.success(equipList)
//        )
//        whenever(
//            saveAllEquip(equipList)
//        ).thenReturn(
//            Result.success(true)
//        )
//        val viewModel = NroEquipProprioViewModel(
//            SavedStateHandle(
//                mapOf(
//                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                    Args.ID_ARGS to 0
//                )
//            ),
//            checkNroEquipProprio,
//            setNroEquip,
//            cleanEquip,
//            getAllEquipServer,
//            saveAllEquip,
//            getNroEquip
//        )
//        val result = viewModel.updateAllDatabase().toList()
//        assertEquals(result.count(), 4)
//        assertEquals(
//            result[0],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Limpando a tabela tb_equip",
//                currentProgress = porc(1f, 4f)
//            )
//        )
//        assertEquals(
//            result[1],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Recuperando dados da tabela tb_equip do Web Service",
//                currentProgress = porc(2f, 4f),
//            )
//        )
//        assertEquals(
//            result[2],
//            NroEquipProprioState(
//                flagProgress = true,
//                msgProgress = "Salvando dados na tabela tb_equip",
//                currentProgress = porc(3f, 4f),
//            )
//        )
//        assertEquals(
//            result[3],
//            NroEquipProprioState(
//                flagDialog = true,
//                flagProgress = false,
//                flagFailure = false,
//                msgProgress = "Atualização de dados realizado com sucesso!",
//                currentProgress = 1f,
//            )
//        )
//    }
//
//    @Test
//    fun `check return success if setTextField update is success`() = runTest {
//        val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//        val setNroEquip = mock<SetNroEquip>()
//        val cleanEquip = mock<CleanEquip>()
//        val getAllEquipServer = mock<GetAllEquipServer>()
//        val saveAllEquip = mock<SaveAllEquip>()
//        val getNroEquip = mock<GetNroEquip>()
//        val equipList = listOf(
//            Equip(
//                idEquip = 10,
//                nroEquip = 100,
//            ),
//        )
//        whenever(
//            cleanEquip()
//        ).thenReturn(
//            Result.success(true)
//        )
//        whenever(
//            getAllEquipServer()
//        ).thenReturn(
//            Result.success(equipList)
//        )
//        whenever(
//            saveAllEquip(equipList)
//        ).thenReturn(
//            Result.success(true)
//        )
//        val viewModel = NroEquipProprioViewModel(
//            SavedStateHandle(
//                mapOf(
//                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
//                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                    Args.ID_ARGS to 0
//                )
//            ),
//            checkNroEquipProprio,
//            setNroEquip,
//            cleanEquip,
//            getAllEquipServer,
//            saveAllEquip,
//            getNroEquip
//        )
//        viewModel.setTextField("ATUALIZAR DADOS", TypeButton.UPDATE)
//        assertEquals(viewModel.uiState.value.flagDialog, true)
//        assertEquals(
//            viewModel.uiState.value.msgProgress,
//            "Atualização de dados realizado com sucesso!"
//        )
//    }
//
//    @Test
//    fun `check return failure usecase if have error in usecase GetNroEquip`() = runTest {
//        val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//        val setNroEquip = mock<SetNroEquip>()
//        val cleanEquip = mock<CleanEquip>()
//        val getAllEquipServer = mock<GetAllEquipServer>()
//        val saveAllEquip = mock<SaveAllEquip>()
//        val getNroEquip = mock<GetNroEquip>()
//        whenever(getNroEquip(1)).thenReturn(
//            Result.failure(
//                UsecaseException(
//                    function = "GetNroEquip",
//                    cause = Exception()
//                )
//            )
//        )
//        val viewModel = NroEquipProprioViewModel(
//            SavedStateHandle(
//                mapOf(
//                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
//                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                    Args.ID_ARGS to 1
//                )
//            ),
//            checkNroEquipProprio,
//            setNroEquip,
//            cleanEquip,
//            getAllEquipServer,
//            saveAllEquip,
//            getNroEquip
//        )
//        viewModel.getNroEquip()
//        assertEquals(viewModel.uiState.value.flagDialog, true)
//        assertEquals(
//            viewModel.uiState.value.failure,
//            "Failure Usecase -> GetNroEquip -> java.lang.Exception"
//        )
//    }
//
//    @Test
//    fun `check return nroEquip if GetNroEquip execute success`() = runTest {
//        val checkNroEquipProprio = mock<CheckNroEquipProprio>()
//        val setNroEquip = mock<SetNroEquip>()
//        val cleanEquip = mock<CleanEquip>()
//        val getAllEquipServer = mock<GetAllEquipServer>()
//        val saveAllEquip = mock<SaveAllEquip>()
//        val getNroEquip = mock<GetNroEquip>()
//        whenever(getNroEquip(1)).thenReturn(
//            Result.success("100")
//        )
//        val viewModel = NroEquipProprioViewModel(
//            SavedStateHandle(
//                mapOf(
//                    Args.FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
//                    Args.TYPE_EQUIP_ARGS to TypeEquip.VEICULO.ordinal,
//                    Args.ID_ARGS to 1
//                )
//            ),
//            checkNroEquipProprio,
//            setNroEquip,
//            cleanEquip,
//            getAllEquipServer,
//            saveAllEquip,
//            getNroEquip
//        )
//        viewModel.getNroEquip()
//        assertEquals(viewModel.uiState.value.nroEquip, "100")
//    }
}