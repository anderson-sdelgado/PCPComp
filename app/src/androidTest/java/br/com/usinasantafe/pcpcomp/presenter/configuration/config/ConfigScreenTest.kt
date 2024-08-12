package br.com.usinasantafe.pcpcomp.presenter.configuration.config

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.ConfigScreen
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.ConfigViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.ui.theme.BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule

import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ConfigScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verify_check_token_correct() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""{"idBD":1}"""))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_input_in_page() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_return_data() = runTest{

        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""{"idBD":1}"""))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(
            Config(
                password = "12345",
                number = 16997417840
            )
        )

        setContent()
        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).assert(hasText("16997417840"))
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).assert(hasText("12345"))
        composeTestRule.waitUntilTimeout(2_000)
    }

    @Test
    fun verify_check_failure_connection() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken -> java.lang.NullPointerException")
    }

    @Test
    fun verify_failure_all_field_empty() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA TODOS OS CAMPOS PARA SALVAR AS CONFIGURAÇÕES E ATUALIZAR TODAS AS BASES DE DADOS.")
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsNotDisplayed()
    }

    @Test
    fun verify_failure_password_empty() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA TODOS OS CAMPOS PARA SALVAR AS CONFIGURAÇÕES E ATUALIZAR TODAS AS BASES DE DADOS.")
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsNotDisplayed()
    }

    @Test
    fun verify_failure_number_empty() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA TODOS OS CAMPOS PARA SALVAR AS CONFIGURAÇÕES E ATUALIZAR TODAS AS BASES DE DADOS.")
        composeTestRule.onNodeWithTag(BUTTON_OK_ALERT_DIALOG_SIMPLE)
            .performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ATENÇÃO").assertIsNotDisplayed()
    }

    @Test
    fun verify_failure_datasource_token_if_return_server_is_empty() = runTest {
        val server = MockWebServer()
        server.dispatcher = dispatcherFailureToken
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken -> java.io.EOFException: End of input at line 1 column 1 path \$")
    }

    @Test
    fun verify_failure_datasource_colab_if_return_server_is_data_incorrect() = runTest {
        val server = MockWebServer()
        server.dispatcher = dispatcherFailureColab
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. Failure Datasource -> ColabRetrofitDatasourceImpl.recoverAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 17 path \$[0].matricColab")
    }

    @Test
    fun verify_failure_datasource_terceiro_if_return_server_is_data_repeated() = runTest {
        val server = MockWebServer()
        server.dispatcher = dispatcherFailureTerceiro
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. Failure Datasource -> TerceiroRoomDatasourceImpl.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_terceiro.idTerceiro (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")
    }

    @Test
    fun verify_success_token_and_update_all_table() {
        val server = MockWebServer()
        server.dispatcher = dispatcherSuccess
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        setContent()
        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("Atualização de dados realizado com sucesso!").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO REALIZADA COM SUCESSO!").assertIsDisplayed()
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

val dispatcherSuccess: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val dispatcherFailureToken: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody("""""")
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val dispatcherFailureColab: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultFailureColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val dispatcherFailureTerceiro: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/terceiro.php" -> return MockResponse().setBody(resultFailureTerceiroRetrofit)
            "/visitante.php" -> return MockResponse().setBody(resultVisitanteRetrofit)
        }
        return MockResponse().setResponseCode(404)
    }
}

val resultTokenRetrofit = """{"idBD":1}""".trimIndent()

val resultColabRetrofit = """
    [{"matricColab":19759,"nomeColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultFailureColabRetrofit = """
    [{"matricColab":19759a,"nomeColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultEquipRetrofit = """
    [{"idEquip":19,"nroEquip":190}]
""".trimIndent()

val resultLocalRetrofit = """
    [{"idLocal":1,"descrLocal":"Usina"}]
""".trimIndent()

val resultTerceiroRetrofit = """
    [{"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"}]
""".trimIndent()

val resultFailureTerceiroRetrofit = """
    [
        {"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"},
        {"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"}
    ]
""".trimIndent()

val resultVisitanteRetrofit = """
    [{"idVisitante":1,"cpfVisitante":"123.456.789-00","nomeVisitante":"Visitante","empresaVisitante":"Empresa Visitante"}]
""".trimIndent()