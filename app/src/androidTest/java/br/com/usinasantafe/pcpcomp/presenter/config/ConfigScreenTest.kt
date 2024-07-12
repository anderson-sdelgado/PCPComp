package br.com.usinasantafe.pcpcomp.presenter.config

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel

class ConfigScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verify_textsAndInput_ConfigPage() {
        setContent()
        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_msgActionButtonOk_allFieldEmpty() {
        setContent()
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsNotDisplayed()
    }

    @Test
    fun verify_msgActionButtonOk_passwordFieldEmpty() {
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsNotDisplayed()
    }

    @Test
    fun verify_msgActionButtonOk_numberFieldEmpty() {
        setContent()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsNotDisplayed()
    }

    private fun setContent() {
        composeTestRule.setContent {
            ConfigScreen (
                viewModel = koinViewModel<ConfigViewModel>(),
                onNavMenuInicial = {},
            )
        }
    }
}