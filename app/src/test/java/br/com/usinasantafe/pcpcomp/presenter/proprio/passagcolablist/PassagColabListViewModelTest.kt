package br.com.usinasantafe.pcpcomp.presenter.proprio.passagcolablist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CleanPassagColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.DeletePassagColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetPassagColabList
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PassagColabListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `Check return failure if CleanPassagColab have failure`() = runTest {
        val cleanPassagColab = mock<CleanPassagColab>()
        val getPassagColabList = mock<GetPassagColabList>()
        val deletePassagColab = mock<DeletePassagColab>()
        whenever(cleanPassagColab()).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "CleanPassagColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagColabListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagColab,
            getPassagColabList,
            deletePassagColab
        )
        viewModel.cleanPassag()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> CleanPassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return failure if have failure in RecoverPassagColab`() = runTest {
        val cleanPassagColab = mock<CleanPassagColab>()
        val getPassagColabList = mock<GetPassagColabList>()
        val deletePassagColab = mock<DeletePassagColab>()
        whenever(
            getPassagColabList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "RecoverPassagColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagColabListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagColab,
            getPassagColabList,
            deletePassagColab
        )
        viewModel.recoverPassag()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> RecoverPassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab if RecoverPassagColab execute successfully`() = runTest {
        val cleanPassagColab = mock<CleanPassagColab>()
        val getPassagColabList = mock<GetPassagColabList>()
        val deletePassagColab = mock<DeletePassagColab>()
        whenever(
            getPassagColabList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                )
            )
        )
        val viewModel = PassagColabListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagColab,
            getPassagColabList,
            deletePassagColab
        )
        viewModel.recoverPassag()
        assertEquals(viewModel.uiState.value.passagList.size, 2)
        assertEquals(viewModel.uiState.value.passagList[0].matricColab, 19759L)
    }

    @Test
    fun `Check return failure if have failure in DeletePassagColab`() = runTest {
        val cleanPassagColab = mock<CleanPassagColab>()
        val getPassagColabList = mock<GetPassagColabList>()
        val deletePassagColab = mock<DeletePassagColab>()
        whenever(
            deletePassagColab(
                19759,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.failure(
                UsecaseException(
                    function = "DeletePassagColab",
                    cause = Exception()
                )
            )
        )
        val viewModel = PassagColabListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagColab,
            getPassagColabList,
            deletePassagColab
        )
        viewModel.setDelete(19759)
        assertEquals(viewModel.uiState.value.flagDialogCheck, true)
        viewModel.deletePassag()
        assertEquals(viewModel.uiState.value.flagDialogCheck, false)
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Usecase -> DeletePassagColab -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return list Colab after deletePassag execute successfully`() = runTest {
        val cleanPassagColab = mock<CleanPassagColab>()
        val getPassagColabList = mock<GetPassagColabList>()
        val deletePassagColab = mock<DeletePassagColab>()
        whenever(
            deletePassagColab(
                19759,
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(true)
        )
        whenever(
            getPassagColabList(
                FlowApp.ADD,
                0
            )
        ).thenReturn(
            Result.success(
                listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                )
            )
        )
        val viewModel = PassagColabListViewModel(
            SavedStateHandle(
                mapOf(
                    Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                    Args.TYPE_OCUPANTE_ARGS to TypeOcupante.MOTORISTA.ordinal,
                    Args.ID_ARGS to 0,
                )
            ),
            cleanPassagColab,
            getPassagColabList,
            deletePassagColab
        )
        viewModel.setDelete(19759)
        assertEquals(viewModel.uiState.value.flagDialogCheck, true)
        viewModel.deletePassag()
        assertEquals(viewModel.uiState.value.flagDialogCheck, false)
        assertEquals(viewModel.uiState.value.passagList.size, 2)
        assertEquals(viewModel.uiState.value.passagList[0].matricColab, 19759L)
    }

}