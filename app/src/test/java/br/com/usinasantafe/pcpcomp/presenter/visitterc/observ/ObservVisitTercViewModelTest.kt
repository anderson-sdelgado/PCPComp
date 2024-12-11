package br.com.usinasantafe.pcpcomp.presenter.visitterc.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetObservVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SaveMovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetObservVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.StartOutputMovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ObservVisitTercViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setObservVisitTerc = mock<SetObservVisitTerc>()
    private val getObservVisitTerc = mock<GetObservVisitTerc>()
    private val startOutputMovEquipVisitTerc = mock<StartOutputMovEquipVisitTerc>()
    private val saveMovEquipVisitTerc = mock<SaveMovEquipVisitTerc>()

    private fun getViewModel(savedStateHandle: SavedStateHandle) =
        ObservVisitTercViewModel(
            savedStateHandle,
            setObservVisitTerc,
            getObservVisitTerc,
            startOutputMovEquipVisitTerc,
            saveMovEquipVisitTerc
        )

    @Test
    fun `Check return failure if have error in GetObserv`() = runTest {
        whenever(
            getObservVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetObservVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> GetObservVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return observ if GetObserv execute successfully`() = runTest {
        whenever(
            getObservVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertEquals(state.observ, "Observação")
    }

    @Test
    fun `Check return failure if have error in StartOutputMovEquipVisitTerc`() = runTest {
        whenever(
            startOutputMovEquipVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "StartOutputMovEquipVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> StartOutputMovEquipVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have error in SetObserv`() = runTest {
        whenever(
            startOutputMovEquipVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetObservVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.OUTPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SetObservVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have error in SaveMovEquipVisitTerc`() = runTest {
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            saveMovEquipVisitTerc(
                typeMov = TypeMovEquip.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveMovEquipVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = getViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            )
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(
            state.failure,
            "Failure Usecase -> SaveMovEquipVisitTerc -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if SetObserv execute successfully`() =
        runTest {
            whenever(
                setObservVisitTerc(
                    observ = "Observação",
                    flowApp = FlowApp.ADD,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveMovEquipVisitTerc(
                    typeMov = TypeMovEquip.INPUT,
                    id = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel(
                SavedStateHandle(
                    mapOf(
                        TYPE_MOV_ARGS to TypeMovEquip.INPUT.ordinal,
                        FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                        ID_ARGS to 1
                    )
                )
            )
            viewModel.onObservChanged("Observação")
            viewModel.setObserv()
            val state = viewModel.uiState.value
            assertTrue(state.flagAccess)
        }
}