package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.config.dispatcherSuccess
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class FlowMainInitialFuncionalTest: KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verify_flow_main_initial() = runTest {
        val server = MockWebServer()
        server.start()
        server.dispatcher = dispatcherSuccess
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.saveConfig(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1,
                flagUpdate = FlagUpdate.UPDATED,
            )
        )

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        composeTestRule.waitUntilTimeout(4_000)

    }

}