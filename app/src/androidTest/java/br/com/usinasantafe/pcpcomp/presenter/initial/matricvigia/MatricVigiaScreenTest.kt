package br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.MatricVigiaScreen
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.MatricVigiaViewModel
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
        composeTestRule.waitUntilTimeout(2_000)
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

    @Test
    fun check_return_failure_if_token_is_invalid() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("{ error : Authorization header is missing }"))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverColabServer -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_failure_if_have_row_repeated() = runTest {
        val resultColabRetrofit = """
            [
                {"matricColab":19759,"nomeColab":"ANDERSON DA SILVA DELGADO"},
                {"matricColab":19759,"nomeColab":"ANDERSON DA SILVA DELGADO"}
            ]
        """.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultColabRetrofit))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1
            )
        )
        setContent()
        composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. Failure Datasource -> ColabRoomDatasourceImpl.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_colab.matricColab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")
    }

    @Test
    fun check_return_success_in_update_data() = runTest {
        val resultColabRetrofit = """
            [
                {"matricColab":19759,"nomeColab":"ANDERSON DA SILVA DELGADO"}
            ]
        """.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultColabRetrofit))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1
            )
        )
        setContent()
        composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsNotDisplayed()
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