package br.com.usinasantafe.pcpcomp.presenter.initial.local

import br.com.usinasantafe.pcpcomp.MainCoroutineRule
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetIdLocalConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.GetLocalList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LocalViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @Test
    fun `check return failure if RecoverLocals have failure`() = runTest {
        val setIdLocalConfig = mock<SetIdLocalConfig>()
        val getLocalList = mock<GetLocalList>()
        whenever(getLocalList()).thenReturn(
            Result.failure(
                DatasourceException(
                    function = "RecoverNomeVigia",
                    cause = Exception()
                )
            )
        )
        val viewModel = LocalViewModel(getLocalList, setIdLocalConfig)
        viewModel.startRecoverLocals()
        assertEquals(viewModel.uiState.value.flagDialog, true)
        assertEquals(viewModel.uiState.value.failure, "Failure Datasource -> RecoverNomeVigia -> java.lang.Exception")
    }

    @Test
    fun `check return name if RecoverNomeVigia is success`() = runTest {
        val locals = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            )
        )
        val setIdLocalConfig = mock<SetIdLocalConfig>()
        val getLocalList = mock<GetLocalList>()
        whenever(getLocalList()).thenReturn(
            Result.success(
                locals
            )
        )
        val viewModel = LocalViewModel(getLocalList, setIdLocalConfig)
        viewModel.startRecoverLocals()
        assertEquals(viewModel.uiState.value.flagDialog, false)
        assertEquals(viewModel.uiState.value.locals, locals)
    }
}