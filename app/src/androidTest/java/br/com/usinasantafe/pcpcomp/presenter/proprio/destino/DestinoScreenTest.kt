package br.com.usinasantafe.pcpcomp.presenter.proprio.destino

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetDestinoProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetTypeMov
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetDestinoProprio
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class DestinoScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val setDestinoProprio: SetDestinoProprio by inject()
    private val getDestinoProprio: GetDestinoProprio by inject()
    private val getTypeMov: GetTypeMov by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_field_destino_is_empty() {
        setContent()
        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"DESTINO\" PARA DAR CONTINUIDADE AO APONTAMENTO.")
    }

    private fun setContent(
        flowApp: FlowApp = FlowApp.ADD,
        pos: Int = 0,
    ) {
        composeTestRule.setContent {
            DestinoProprioScreen(
                viewModel = DestinoProprioViewModel(
                    SavedStateHandle(
                        mapOf(
                            Args.FLOW_APP_ARGS to flowApp.ordinal,
                            Args.ID_ARGS to pos
                        )
                    ),
                    setDestinoProprio,
                    getDestinoProprio,
                    getTypeMov
                ),
                onNavPassagList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {}
            )
        }
    }
}