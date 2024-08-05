package br.com.usinasantafe.pcpcomp.presenter.local

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.presenter.nomevigia.NomeVigiaScreen
import br.com.usinasantafe.pcpcomp.presenter.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class LocalScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_listLocal_if_have_local_in_table() = runTest {
        val saveAllLocal: SaveAllLocal by inject()
        saveAllLocal(
            listOf(
                Local(
                    idLocal = 1,
                    descrLocal = "USINA"
                )
            )
        )
        setContent()
        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("1 - USINA").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
    }

    private fun setContent() {
        composeTestRule.setContent {
            LocalScreen(
                viewModel = koinViewModel<LocalViewModel>(),
                onNavNomeVigia = {},
                onNavMenuApont = {}
            )
        }
    }

}