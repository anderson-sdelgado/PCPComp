package br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CleanPassagColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.DeletePassagColab
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetPassagColabList
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import br.com.usinasantafe.pcpcomp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class PassagColabListScreenTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val cleanPassagColab: CleanPassagColab by inject()
    private val getPassagColabList: GetPassagColabList by inject()
    private val deletePassagColab: DeletePassagColab by inject()

    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource by inject()
    private val colabDao: ColabDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_list_clean() = runTest {
        setContent()
        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(5_000)
    }

    @Test
    fun check_return_failure_if_not_have_data_in_colab_table() = runTest {
        movEquipProprioPassagSharedPreferencesDatasource.add(19759)
        setContent(typeOcupante = TypeOcupante.PASSAGEIRO)
        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverPassagColab -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_list_if_process_execute_success() = runTest {
        movEquipProprioPassagSharedPreferencesDatasource.add(19759)
        movEquipProprioPassagSharedPreferencesDatasource.add(19035)
        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE"
                )
            )
        )
        setContent(typeOcupante = TypeOcupante.PASSAGEIRO)
        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("19759 - ANDERSON DA SILVA DELGADO").assertIsDisplayed()
    }

    @Test
    fun check_delete_item_from_list() = runTest {
        movEquipProprioPassagSharedPreferencesDatasource.add(19759)
        movEquipProprioPassagSharedPreferencesDatasource.add(19035)
        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE"
                )
            )
        )
        setContent(typeOcupante = TypeOcupante.PASSAGEIRO)
        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("19759 - ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("19759 - ANDERSON DA SILVA DELGADO").performClick()
        composeTestRule.onNodeWithText("SIM").performClick()
        composeTestRule.onNodeWithText("19759 - ANDERSON DA SILVA DELGADO").assertIsNotDisplayed()
        composeTestRule.waitUntilTimeout(5_000)
    }

    private fun setContent(
        flowApp: FlowApp = FlowApp.ADD,
        typeOcupante: TypeOcupante = TypeOcupante.MOTORISTA,
        pos: Int = 0
    ) {
        composeTestRule.setContent {
//            PassagColabListScreen(
//                viewModel = PassagColabListViewModel(
//                    SavedStateHandle(
//                        mapOf(
//                            Args.FLOW_APP_ARGS to flowApp.ordinal,
//                            Args.TYPE_OCUPANTE_ARGS to typeOcupante.ordinal,
//                            Args.ID_ARGS to pos,
//                        )
//                    ),
//                    cleanPassagColab,
//                    getPassagColabList,
//                    deletePassagColab
//                ),
//                onNavMatricMotorista = {},
//                onNavDetalheProprio = {},
//                onNavMatricPassag = {},
//                onNavDestino = {}
//            )
        }
    }

}