package br.com.usinasantafe.pcpcomp.presenter.chave.controleeditlist

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.CloseAllMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetMovChaveOpenList
import br.com.usinasantafe.pcpcomp.presenter.chave.model.ControleChaveModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ControleChaveEditListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getMovChaveOpenList = mock<GetMovChaveOpenList>()
    private val closeAllMovChave = mock<CloseAllMovChave>()
    private val getViewmModel = ControleChaveEditListViewModel(
        getMovChaveOpenList,
        closeAllMovChave
    )

    @Test
    fun `recoverControleChaveOpenList - Check return failure if have error in GetMovChaveOpenList`() =
        runTest {
            whenever(
                getMovChaveOpenList()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetMovChaveOpenList",
                        cause = Exception()
                    )
                )
            )
            getViewmModel.recoverMovOpenList()
            val state = getViewmModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> GetMovChaveOpenList -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverControleChaveOpenList - Check return correct if function execute successfully`() =
        runTest {
            val list = listOf(
                ControleChaveModel(
                    id = 1,
                    dthr = "08/08/2024 12:00",
                    tipoMov = "RETIRADA",
                    chave = "Chave 1",
                    colab = "Anderson da Silva Delgado"
                )
            )
            whenever(
                getMovChaveOpenList()
            ).thenReturn(
                Result.success(
                    list
                )
            )
            getViewmModel.recoverMovOpenList()
            val state = getViewmModel.uiState.value
            assertEquals(
                state.flagDialog,
                false
            )
            val listState = state.controleChaveModelList
            assertEquals(
                listState.size,
                1
            )
            val model = listState[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.dthr,
                "08/08/2024 12:00"
            )
            assertEquals(
                model.tipoMov,
                "RETIRADA"
            )
            assertEquals(
                model.chave,
                "Chave 1"
            )
            assertEquals(
                model.colab,
                "Anderson da Silva Delgado"
            )
        }

    @Test
    fun `closeAllMov - Check return failure if have error in CloseAllMovChave`() =
        runTest {
            whenever(
                closeAllMovChave()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "CloseAllMovChave",
                        cause = Exception()
                    )
                )
            )
            getViewmModel.closeAllMov()
            val state = getViewmModel.uiState.value
            assertEquals(
                state.flagDialog,
                true
            )
            assertEquals(
                state.failure,
                "Failure Usecase -> CloseAllMovChave -> java.lang.Exception"
            )
        }

    @Test
    fun `closeAllMov - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                closeAllMovChave()
            ).thenReturn(
                Result.success(
                    true
                )
            )
            getViewmModel.closeAllMov()
            val state = getViewmModel.uiState.value
            assertEquals(
                state.flagDialog,
                false
            )
            assertEquals(
                state.flagCloseAllMov,
                true
            )
        }
}