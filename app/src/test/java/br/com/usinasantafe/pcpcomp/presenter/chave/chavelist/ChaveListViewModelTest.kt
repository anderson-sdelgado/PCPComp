package br.com.usinasantafe.pcpcomp.presenter.chave.chavelist

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetChaveList
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.SetIdChaveMovChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.UpdateLocalTrab
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ChaveListGetViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getChaveList = mock<GetChaveList>()
    private val updateChave = mock<UpdateChave>()
    private val updateLocalTrab = mock<UpdateLocalTrab>()
    private val setIdChaveMovChave = mock<SetIdChaveMovChave>()
    private fun getViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(
            mapOf(
                Args.FLOW_APP_ARGS to FlowApp.ADD.ordinal,
                Args.ID_ARGS to 0,
            )
        )
    ) = ChaveListViewModel(
        savedStateHandle = savedStateHandle,
        getChaveList = getChaveList,
        updateChave = updateChave,
        updateLocalTrab = updateLocalTrab,
        setIdChaveMovChave = setIdChaveMovChave
    )
    private val sizeAll = 7f

    @Test
    fun `recoverList - Check return failure if have error in GetChaveList`() =
        runTest {
            whenever(
                getChaveList()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetChaveList",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetChaveList -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverList - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                getChaveList()
            ).thenReturn(
                Result.success(
                    listOf(
                        ChaveModel(
                            1,
                            "01 - SALA TI - TI"
                        ),
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.recoverList()
            val list = viewModel.uiState.value.chaveList
            assertEquals(
                list[0].id,
                1
            )
            val model = list[0]
            assertEquals(
                model.descr,
                "01 - SALA TI - TI"
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateChave`() =
        runTest {
            whenever(
                updateChave(
                    count = 1f,
                    sizeAll = sizeAll
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_chave",
                        currentProgress = percentage(1f, sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.updateDatabase()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(1f, sizeAll)
                )
            )
            assertEquals(
                result[1],
                ChaveListState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanChave -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "Failure Usecase -> CleanChave -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `updateDatabase - Check return failure if have error in UpdateLocalTrab`() =
        runTest {
            whenever(
                updateChave(
                    sizeAll = sizeAll,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_chave",
                        currentProgress = percentage(1f, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                        currentProgress = percentage(2f, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_chave",
                        currentProgress = percentage(3f, sizeAll)
                    ),
                )
            )
            whenever(
                updateLocalTrab(
                    count = 2f,
                    sizeAll = sizeAll
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_local_trab",
                        currentProgress = percentage(4f, sizeAll)
                    ),
                    ResultUpdate(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                        msgProgress = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                        currentProgress = 1f,
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.updateDatabase()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                5
            )
            assertEquals(
                result[0],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = percentage(4f, 7f)
                )
            )
            assertEquals(
                result[4],
                ChaveListState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                    msgProgress = "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException",
                    currentProgress = 1f,
                )
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "Failure Usecase -> CleanLocalTrab -> java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return success if UpdateAllTable execute successfully`() =
        runTest {
            whenever(
                updateChave(
                    sizeAll = sizeAll,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_chave",
                        currentProgress = percentage(1f, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                        currentProgress = percentage(2f, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_chave",
                        currentProgress = percentage(3f, sizeAll)
                    ),
                )
            )
            whenever(
                updateLocalTrab(
                    sizeAll = sizeAll,
                    count = 2f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Limpando a tabela tb_local_trab",
                        currentProgress = percentage(4f, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                        currentProgress = percentage(5f, sizeAll)
                    ),
                    ResultUpdate(
                        flagProgress = true,
                        msgProgress = "Salvando dados na tabela tb_local_trab",
                        currentProgress = percentage(6f, sizeAll)
                    ),
                )
            )
            val viewModel = getViewModel()
            viewModel.updateDatabase()
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                7
            )
            assertEquals(
                result[0],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_chave",
                    currentProgress = percentage(1f, 7f)
                )
            )
            assertEquals(
                result[1],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_chave do Web Service",
                    currentProgress = percentage(2f, 7f)
                )
            )
            assertEquals(
                result[2],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_chave",
                    currentProgress = percentage(3f, 7f)
                )
            )
            assertEquals(
                result[3],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Limpando a tabela tb_local_trab",
                    currentProgress = percentage(4f, sizeAll)
                )
            )
            assertEquals(
                result[4],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Recuperando dados da tabela tb_local_trab do Web Service",
                    currentProgress = percentage(5f, sizeAll)
                )
            )
            assertEquals(
                result[5],
                ChaveListState(
                    flagProgress = true,
                    msgProgress = "Salvando dados na tabela tb_local_trab",
                    currentProgress = percentage(6f, sizeAll)
                )
            )
            assertEquals(
                result[6],
                ChaveListState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    msgProgress = "Atualização de dados realizado com sucesso!",
                    currentProgress = 1f,
                )
            )
            assertEquals(
                viewModel.uiState.value.msgProgress,
                "Atualização de dados realizado com sucesso!"
            )
        }

    @Test
    fun `setIdChave - Check return failure if have error in Usecase SetIdChave`() =
        runTest {
            whenever(
                setIdChaveMovChave(
                    idChave = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "SetIdChaveMov",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getViewModel()
            viewModel.setIdChave(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> SetIdChaveMov -> java.lang.Exception"
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                setIdChaveMovChave(
                    idChave = 1,
                    flowApp = FlowApp.ADD,
                    id = 0
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getViewModel()
            viewModel.setIdChave(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}