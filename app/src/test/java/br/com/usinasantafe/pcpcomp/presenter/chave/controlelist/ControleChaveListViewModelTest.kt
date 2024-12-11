package br.com.usinasantafe.pcpcomp.presenter.chave.controlelist

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.GetMovChaveRemoveList
import br.com.usinasantafe.pcpcomp.domain.usecases.chave.StartRemoveChave
import br.com.usinasantafe.pcpcomp.domain.usecases.common.GetHeader
import br.com.usinasantafe.pcpcomp.presenter.chave.model.ControleChaveModel
import br.com.usinasantafe.pcpcomp.presenter.model.HeaderModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ControleChaveListGetViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getHeader = mock<GetHeader>()
    private val getMovChaveRemoveList = mock<GetMovChaveRemoveList>()
    private val startRemoveChave = mock<StartRemoveChave>()

    private fun getGetViewModel() = ControleChaveListViewModel(
        getHeader = getHeader,
        getMovChaveRemoveList = getMovChaveRemoveList,
        startRemoveChave = startRemoveChave
    )

    @Test
    fun `Check return failure if have error in getHeader`() = runTest {
        whenever(
            getHeader()
        ).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "ConfigSharedPreferences.hasConfig",
                    cause = Exception()
                )
            )
        )
        val viewModel = getGetViewModel()
        viewModel.returnHeader()
        assertEquals(
            viewModel.uiState.value.flagDialog,
            true
        )
        assertEquals(
            viewModel.uiState.value.failure,
            "Failure Datasource -> ConfigSharedPreferences.hasConfig -> java.lang.Exception"
        )
    }

    @Test
    fun `Check return true if GetHeader execute successfully`() =
        runTest {
            whenever(
                getHeader()
            ).thenReturn(
                Result.success(
                    HeaderModel(
                        descrVigia = "19759 - Anderson da Silva Delgado",
                        descrLocal = "1 - Usina"
                    )
                )
            )
            val viewModel = getGetViewModel()
            viewModel.returnHeader()
            assertEquals(
                viewModel.uiState.value.descrVigia,
                "19759 - Anderson da Silva Delgado"
            )
            assertEquals(
                viewModel.uiState.value.descrLocal,
                "1 - Usina"
            )
        }

    @Test
    fun `Check return failure if have error in GetControleChaveRemoveList`() =
        runTest {
            whenever(
                getMovChaveRemoveList()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "GetControleChaveRemoveList",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getGetViewModel()
            viewModel.recoverControleChaveList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> GetControleChaveRemoveList -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if GetControleChaveRemoveList execute successfully`() =
        runTest {
            whenever(
                getMovChaveRemoveList()
            ).thenReturn(
                Result.success(
                    listOf(
                        ControleChaveModel(
                            id = 1,
                            chave = "01 - SALA TI - TI",
                            dthr = "04/12/2024 10:20",
                            colab = "19795 - ANDERSON DA SILVA DELGADO"
                        )
                    )
                )
            )
            val viewModel = getGetViewModel()
            viewModel.recoverControleChaveList()
            val entityList = viewModel.uiState.value.controleChaveModelList
            assertEquals(
                entityList.size,
                1
            )
            val entity = entityList[0]
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.chave,
                "01 - SALA TI - TI"
            )
        }

    @Test
    fun `Check return failure if have error in StartRemoveChave`() =
        runTest {
            whenever(
                startRemoveChave()
            ).thenReturn(
                Result.failure(
                    UsecaseException(
                        function = "StartRemoveChave",
                        cause = Exception()
                    )
                )
            )
            val viewModel = getGetViewModel()
            viewModel.startMov()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "Failure Usecase -> StartRemoveChave -> java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if StartRemoveChave execute successfully`() =
        runTest {
            whenever(
                startRemoveChave()
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = getGetViewModel()
            viewModel.startMov()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
        }
}