package br.com.usinasantafe.pcpcomp.presenter.senha

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SenhaScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_texts_in_page() {
        setContent()
        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("12345")
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_password_null() = runTest {

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(Config(password = "12345"))

        setContent()
        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK")
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsDisplayed()

    }

    @Test
    fun verify_password_incorrect() = runTest {

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(Config(password = "12345"))

        setContent()
        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("123456")
        composeTestRule.onNodeWithText("OK")
            .performClick()
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)

    }

    private fun setContent() {
        composeTestRule.setContent {
            SenhaScreen(
                viewModel = koinViewModel<SenhaViewModel>(),
                onNavMenuInicial = {},
                onNavConfig = {},
            )
        }
    }

}
