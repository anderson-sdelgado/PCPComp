package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ConfigFlowTest: KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verify_flow_configuration_initial() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher = dispatcherSuccess
        loadKoinModules(generateTestAppComponent(server.url("").toString()))

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN).performTextInput("16997417840")
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN).performTextInput("12345")
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS")
            .performClick()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
    }

    @Test
    fun verify_flow_configuration_update() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher = dispatcherSuccess
        loadKoinModules(generateTestAppComponent(server.url("").toString()))

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1
            )
        )

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("CONFIGURAÇÃO")
            .performClick()

        composeTestRule.onNodeWithText("SENHA:").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN).performTextInput("12345")

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.onNodeWithText("NRO APARELHO:").assertIsDisplayed()
        composeTestRule.onNodeWithText("SALVAR/ATUALIZAR DADOS")
            .performClick()

        composeTestRule.onNodeWithText("OK")
            .performClick()

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()

    }


}

val dispatcherSuccess: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(resultColabRetrofit)
            "/equip.php" -> return MockResponse().setBody(resultEquipRetrofit)
            "/fluxo.php" -> return MockResponse().setBody(resultFluxoRetrofit)
            "/local.php" -> return MockResponse().setBody(resultLocalRetrofit)
            "/r_local_fluxo.php" -> return MockResponse().setBody(resultRLocalFluxoRetrofit)
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

val resultFluxoRetrofit = """
    [{"idFluxo":1,"descrFluxo":"MOV. EQUIP. PRÓPRIO"}]
""".trimIndent()

val resultLocalRetrofit = """
    [{"idLocal":1,"descrLocal":"Usina"}]
""".trimIndent()

val resultRLocalFluxoRetrofit = """
    [{"idRLocalFluxo":1,"idFluxo":1,"idLocal":1}]
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