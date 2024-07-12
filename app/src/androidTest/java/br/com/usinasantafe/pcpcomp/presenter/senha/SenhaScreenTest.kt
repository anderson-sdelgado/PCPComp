package br.com.usinasantafe.pcpcomp.presenter.senha

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel

class SenhaScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verify_textsInSenhaPage() {
        setContent()
        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("12345")
        composeTestRule.waitUntilTimeout(10_000)
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