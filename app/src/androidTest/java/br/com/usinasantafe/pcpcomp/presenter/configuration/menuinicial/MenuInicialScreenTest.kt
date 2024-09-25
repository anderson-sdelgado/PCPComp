package br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
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

class MenuInicialScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_texts_in_page() = runTest {
        setContent()
        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()
    }

    @Test
    fun check_blocked_if_not_have_data_config_internal() = runTest {
        setContent()
        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("ACESSO NEGADO! POR FAVOR, CONFIGURE O APLICATIVO ANTES DO USO DO MESMO!")
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun check_blocked_if_config_have_incomplete() = runTest {
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(Config())
        setContent()
        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("ACESSO NEGADO! POR FAVOR, CONFIGURE O APLICATIVO ANTES DO USO DO MESMO!")
        composeTestRule.waitUntilTimeout(2_000)
    }

    private fun setContent() {
        composeTestRule.setContent {
            MenuInicialScreen(
                viewModel = koinViewModel<MenuInicialViewModel>(),
                onNavMatricVigia = {},
                onNavSenha = {},
            )
        }
    }
}