package br.com.usinasantafe.pcpcomp.presenter.menuinicial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import org.junit.Rule

import org.junit.Test

class MenuInicialScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verify_textsInMenuInicialPage() {
        setContent()
        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()
    }

    private fun setContent() {
        composeTestRule.setContent {
            PCPCompTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MenuInicialContent(
                        modifier = Modifier.padding(innerPadding), onNavSenha = {},
                    )
                }
            }
        }
    }
}