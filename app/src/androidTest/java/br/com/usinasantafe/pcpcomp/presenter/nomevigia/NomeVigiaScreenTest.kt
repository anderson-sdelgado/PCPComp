package br.com.usinasantafe.pcpcomp.presenter.nomevigia

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class NomeVigiaScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_colab_table() = runTest {
        setContent()
        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Error RecoverNomeVigia -> Failure Usecase -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_nomeColab_if_have_vigia_in_table() = runTest {
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        val colabRoomDatasource: ColabRoomDatasource by inject()
        configSharedPreferences.saveConfig(
            Config(matricVigia = 19759)
        )
        colabRoomDatasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        setContent()
        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
    }

    private fun setContent() {
        composeTestRule.setContent {
            NomeVigiaScreen(
                viewModel = koinViewModel<NomeVigiaViewModel>(),
                onNavMatricVigia = {},
                onNavLocal = {}
            )
        }
    }

}