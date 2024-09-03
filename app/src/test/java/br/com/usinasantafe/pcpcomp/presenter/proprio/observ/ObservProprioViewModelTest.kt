package br.com.usinasantafe.pcpcomp.presenter.proprio.observ

import androidx.compose.runtime.internal.composableLambda
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SaveMovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetObservProprio
import br.com.usinasantafe.pcpcomp.presenter.Args
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
class ObservProprioViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if have failure in GetTypeMov`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            getTypeMov()
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "GetTypeMov",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.setReturn()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> GetTypeMov -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return TypeMov if GetTypeMov execute success`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            getTypeMov()
        ).thenReturn(
            Result.success(TypeMov.INPUT)
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.setReturn()
        assertEquals(viewModel.uiState.value.flagReturn, true)
        assertEquals(viewModel.uiState.value.typeMov, TypeMov.INPUT)
    }

    @Test
    fun `Check return access if execute SetObservProprio with field empty`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

    @Test
    fun `Check return failure if have errors in SetObservProprio`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            setObservProprio(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SetObservProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.onObservChanged("Teste")
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SetObservProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure with observ empty if have errors in SaveMovEquipProprio`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(saveMovEquipProprio()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveMovEquipProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SaveMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have errors in SaveMovEquipProprio`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            setObservProprio(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(saveMovEquipProprio()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "SaveMovEquipProprio",
                    cause = Exception()
                )
            )
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.onObservChanged("Teste")
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> SaveMovEquipProprio -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return access if SetObservProprio execute success`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(
            setObservProprio(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(saveMovEquipProprio()).thenReturn(
            Result.success(true)
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.onObservChanged("Teste")
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }

    @Test
    fun `Check return access with observ empty if SetObservProprio execute success`() = runTest {
        val setObservProprio = mock<SetObservProprio>()
        val saveMovEquipProprio = mock<SaveMovEquipProprio>()
        val getTypeMov = mock<GetTypeMov>()
        whenever(saveMovEquipProprio()).thenReturn(
            Result.success(true)
        )
        val viewModel = ObservProprioViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            setObservProprio,
            saveMovEquipProprio,
            getTypeMov
        )
        viewModel.setObserv()
        assertEquals(viewModel.uiState.value.flagAccess, true)
    }
}