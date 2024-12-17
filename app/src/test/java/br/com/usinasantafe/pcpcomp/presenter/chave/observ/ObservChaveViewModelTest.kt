package br.com.usinasantafe.pcpcomp.presenter.chave.observ

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetObservMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SaveMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SetObservMovChave
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ObservChaveViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setObservMovChave = mock<SetObservMovChave>()
    private val saveMovChave = mock<SaveMovChave>()
    private val getObservMovChave = mock<GetObservMovChave>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS  to FlowApp.ADD.ordinal,
                Args.TYPE_MOV_ARGS  to TypeMovKey.REMOVE.ordinal,
                Args.ID_ARGS to 0
            )
        )
    ) = ObservChaveViewModel(
        savedStateHandle,
        setObservMovChave,
        saveMovChave,
        getObservMovChave
    )

    @Test
    fun `setObserv - Check return failure if have error in SetObservMovChave - REMOVE - ADD`() =
        runTest {
            whenever(
                setObservMovChave(
                    flowApp = FlowApp.ADD,
                    id = 0,
                    observ = null
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SetObservMovChaveImpl",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> SetObservMovChaveImpl -> java.lang.Exception"
            )
        }

    @Test
    fun `setObserv - Check return failure if have error in SaveMovChave - REMOVE - ADD`() =
        runTest {
            whenever(
                setObservMovChave(
                    flowApp = FlowApp.ADD,
                    id = 0,
                    observ = null
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveMovChave(
                    typeMov = TypeMovKey.REMOVE,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SaveMovChaveImpl",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> SaveMovChaveImpl -> java.lang.Exception"
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully - REMOVE - ADD`() =
        runTest {
            whenever(
                setObservMovChave(
                    flowApp = FlowApp.ADD,
                    id = 0,
                    observ = null
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                saveMovChave(
                    typeMov = TypeMovKey.REMOVE,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel()
            viewModel.setObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `getObserv - Check return failure if have error in GetObservMovChave`() =
        runTest {
            whenever(
                getObservMovChave(
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetObservMovChaveImpl",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.getObserv()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetObservMovChaveImpl -> java.lang.Exception"
            )
        }

    @Test
    fun `getObserv - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getObservMovChave(
                    id = 0
                )
            ).thenReturn(
                Result.success("OBSERV")
            )
            val viewModel = getViewModel()
            viewModel.getObserv()
            assertEquals(
                viewModel.uiState.value.flagGetObserv,
                false
            )
            assertEquals(
                viewModel.uiState.value.observ,
                "OBSERV"
            )
        }
}