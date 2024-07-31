package br.com.usinasantafe.pcpcomp.presenter.matricvigia

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest

class MatricVigiaScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun test_initial_click_button_add_char() {
        setContent()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.waitUntilTimeout(10_000)
    }

    @Test
    fun test_initial_click_button_clear_char() {
        setContent()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun check_show_msg_when_click_button_if_field_is_empty() {
        setContent()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"MATRICULA VIGIA\" PARA DAR CONTINUIDADE AO APONTAMENTO.")
    }

    @Test
    fun check_return_failure_when_matric_is_invalid() {
        setContent()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"MATRICULA VIGIA\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")
    }

    private fun setContent() {
        composeTestRule.setContent {
            MatricVigiaScreen(
                viewModel = koinViewModel<MatricVigiaViewModel>(),
                onNavMenuInicial = {},
                onNavNomeVigia = {},
            )
        }
    }
}