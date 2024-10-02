package br.com.usinasantafe.pcpcomp.presenter.residencia.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.GetObservResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SaveMovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetObservResidencia
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
class ObservResidenciaViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have error in GetObserv`() = runTest {
        val setObservResidencia = mock<SetObservResidencia>()
        val getObservResidencia = mock<GetObservResidencia>()
        val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()
        whenever(
            getObservResidencia(
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetObservResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            ),
            setObservResidencia,
            getObservResidencia,
            saveMovEquipResidencia
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> GetObservResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check return observ if GetObserv execute successfully`() = runTest {
        val setObservResidencia = mock<SetObservResidencia>()
        val getObservResidencia = mock<GetObservResidencia>()
        val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()
        whenever(
            getObservResidencia(
                id = 1
            )
        ).thenReturn(
            Result.success("Observação")
        )
        val viewModel = ObservResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.CHANGE.ordinal,
                    ID_ARGS to 1
                )
            ),
            setObservResidencia,
            getObservResidencia,
            saveMovEquipResidencia
        )
        viewModel.recoverObserv()
        val state = viewModel.uiState.value
        assertEquals(state.observ, "Observação")
    }

    @Test
    fun `Check return failure if have error in SetObserv`() = runTest {
        val setObservResidencia = mock<SetObservResidencia>()
        val getObservResidencia = mock<GetObservResidencia>()
        val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetObservResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 0
                )
            ),
            setObservResidencia,
            getObservResidencia,
            saveMovEquipResidencia
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SetObservResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check return failure if have error in SaveMovEquipResidencia`() = runTest {
        val setObservResidencia = mock<SetObservResidencia>()
        val getObservResidencia = mock<GetObservResidencia>()
        val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            saveMovEquipResidencia(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveMovEquipResidencia",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            ),
            setObservResidencia,
            getObservResidencia,
            saveMovEquipResidencia
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagDialog)
        assertEquals(state.failure, "Failure Usecase -> SaveMovEquipResidencia -> java.lang.Exception")
    }

    @Test
    fun `Check access true if SetObserv execute successfully`() = runTest {
        val setObservResidencia = mock<SetObservResidencia>()
        val getObservResidencia = mock<GetObservResidencia>()
        val saveMovEquipResidencia = mock<SaveMovEquipResidencia>()
        whenever(
            setObservResidencia(
                observ = "Observação",
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            saveMovEquipResidencia(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                id = 1
            )
        ).thenReturn(
            Result.success(true)
        )
        val viewModel = ObservResidenciaViewModel(
            SavedStateHandle(
                mapOf(
                    TYPE_MOV_ARGS to TypeMov.INPUT.ordinal,
                    FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    ID_ARGS to 1
                )
            ),
            setObservResidencia,
            getObservResidencia,
            saveMovEquipResidencia
        )
        viewModel.onObservChanged("Observação")
        viewModel.setObserv()
        val state = viewModel.uiState.value
        assertTrue(state.flagAccess)
    }
}