package br.com.usinasantafe.pcpcomp.presenter.visitterc.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.GetObservVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetObservVisitTerc
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov
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

    @Test
    fun `Check return failure if have error in GetObservVisitTerc`() = runTest {
        val setObservVisitTerc = mock<SetObservVisitTerc>()
        val getObservVisitTerc = mock<GetObservVisitTerc>()
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
        val viewModel = ObservVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            ),
            setObservVisitTerc,
            getObservVisitTerc
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> GetObservVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return observ if GetObservVisitTerc execute successfully`() = runTest {
        val setObservVisitTerc = mock<SetObservVisitTerc>()
        val getObservVisitTerc = mock<GetObservVisitTerc>()
        whenever(
            getObservVisitTerc(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val viewModel = ObservVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            ),
            setObservVisitTerc,
            getObservVisitTerc
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertEquals(state.observ, "Observação")
    }

    @Test
    fun `Check return failure if have error in SetObservVisitTerc`() = runTest {
        val setObservVisitTerc = mock<SetObservVisitTerc>()
        val getObservVisitTerc = mock<GetObservVisitTerc>()
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetObservVisitTerc",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 0
                )
            ),
            setObservVisitTerc,
            getObservVisitTerc
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SetObservVisitTerc -> java.lang.Exception")
    }

    @Test
    fun `Check return true if SetObservVisitTerc execute successfully`() = runTest {
        val setObservVisitTerc = mock<SetObservVisitTerc>()
        val getObservVisitTerc = mock<GetObservVisitTerc>()
        whenever(
            setObservVisitTerc(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = ObservVisitTercViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 0
                )
            ),
            setObservVisitTerc,
            getObservVisitTerc
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagAccess)
    }
}