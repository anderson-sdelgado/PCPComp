package br.com.usinasantafe.pcpcomp.presenter.initial.menuapont

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcpcomp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class MenuApontScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() {
        setContent()
        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverHeader -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_nomeColab_if_have_vigia_in_table() = runTest {
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        val colabRoomDatasource: ColabRoomDatasource by inject()
        val localRoomDatasource: LocalRoomDatasource by inject()
        configSharedPreferences.save(
            Config(
                matricVigia = 19759,
                idLocal = 1
            )
        )
        colabRoomDatasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        localRoomDatasource.addAll(
            listOf(
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "1 - USINAS"
                )
            )
        )
        setContent()
        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOCAL: 1 - USINAS").assertIsDisplayed()
    }

    @Test
    fun check_msg_if_click_button_out() = runTest {
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        val colabRoomDatasource: ColabRoomDatasource by inject()
        val localRoomDatasource: LocalRoomDatasource by inject()
        configSharedPreferences.save(
            Config(
                matricVigia = 19759,
                idLocal = 1
            )
        )
        colabRoomDatasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        localRoomDatasource.addAll(
            listOf(
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "1 - USINAS"
                )
            )
        )
        setContent()
        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("LOCAL: 1 - USINAS").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_check").assertTextEquals("DESEJA REALMENTE RETORNAR? ISSO FECHARÁ TODOS OS MOVIMENTOS.")

    }

    private fun setContent() {
        composeTestRule.setContent {
            MenuApontScreen(
                viewModel = koinViewModel<MenuApontViewModel>(),
                onNavMovVeicResidencia = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicProprio = {},
                onNavMovChave = {},
                onNavMovChaveEquip = {},
                onNavSplashScreen = {}
            )
        }
    }
}