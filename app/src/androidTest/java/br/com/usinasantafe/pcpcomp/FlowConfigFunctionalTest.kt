package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.senha.TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN
import br.com.usinasantafe.pcpcomp.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class FlowConfigFunctionalTest: KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verify_flow_configuration() = runTest {

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()

        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_flow_password_incorrect() = runTest {

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(Config(passwordConfig = "12345"))

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("ATENÇÃO").assertIsDisplayed()

        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()

        composeTestRule.waitUntilTimeout(2_000)

    }

    @Test
    fun verify_flow_password_correct() = runTest {

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(Config(passwordConfig = "12345"))

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("12345")

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()

        composeTestRule.waitUntilTimeout(2_000)

    }

    @Test
    fun verify_flow_password_return_data_config() = runTest {

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(Config(
            passwordConfig = "12345",
            numberConfig = 16997417840
        ))

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("12345")

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.waitUntilTimeout(2_000)

        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).assert(hasText("16997417840"))
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).assert(hasText("12345"))

        composeTestRule.waitUntilTimeout(2_000)

    }

}