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
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.dispatcherSuccess
import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class FlowConfigFunctionalTest: KoinTest {

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