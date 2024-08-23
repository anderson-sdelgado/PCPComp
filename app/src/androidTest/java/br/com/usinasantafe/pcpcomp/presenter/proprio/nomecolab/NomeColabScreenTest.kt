package br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.RecoverNomeColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetMatricColab
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utlis.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class NomeColabScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val recoverNomeColab: RecoverNomeColab by inject()
    val setMatricColab: SetMatricColab by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_colab_table() = runTest {
        setContent(matricColab = "19759")
        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverNomeColab -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_nomeColab_if_have_vigia_in_table() = runTest {
        val colabRoomDatasource: ColabRoomDatasource by inject()
        colabRoomDatasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        setContent(matricColab = "19759")
        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
    }

    private fun setContent(
        flowApp: FlowApp = FlowApp.ADD,
        typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
        pos: Int = 0,
        matricColab: String
    ) {
        composeTestRule.setContent {
            NomeColabScreen(
                viewModel = NomeColabViewModel(
                    SavedStateHandle(
                        mapOf(
                            Args.FLOW_APP_ARGS to flowApp.ordinal,
                            Args.TYPE_OCUPANTE_ARGS to typeOcupante.ordinal,
                            Args.ID_ARGS to pos,
                            Args.MATRIC_COLAB_ARGS to matricColab
                        )
                    ),
                    recoverNomeColab,
                    setMatricColab
                ),
                onNavMatricColab = {},
                onNavPassagColabList = {}
            )
        }
    }

}