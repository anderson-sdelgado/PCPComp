package br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CleanEquipSeg
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.DeleteEquipSeg
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.GetEquipSegList
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcpcomp.presenter.Args
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.waitUntilTimeout
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules

import org.koin.test.KoinTest
import org.koin.test.inject

class EquipSegListScreenTest: KoinTest {

    private val cleanEquipSeg: CleanEquipSeg by inject()
    private val getEquipSegList: GetEquipSegList by inject()
    private val deleteEquipSeg: DeleteEquipSeg by inject()

    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()
    private val equipDao: EquipDao by inject()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_list_clean() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        setContent()
        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(5_000)
    }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_table() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        setContent(typeEquip = TypeEquip.VEICULOSEG)
        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
        composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. Failure Usecase -> RecoverEquipSeg -> java.lang.NullPointerException")
    }

    @Test
    fun check_return_list_if_process_execute_success() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        movEquipProprioEquipSegSharedPreferencesDatasource.add(20)
        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "teste"
                ),
                EquipRoomModel(
                    idEquip = 20,
                    nroEquip = 200,
                    descrEquip = "teste"
                )
            )
        )
        setContent(typeEquip = TypeEquip.VEICULOSEG)
        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.waitUntilTimeout(2_000)
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
    }

    @Test
    fun check_delete_item_from_list() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        movEquipProprioEquipSegSharedPreferencesDatasource.add(20)
        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                    descrEquip = "teste"
                ),
                EquipRoomModel(
                    idEquip = 20,
                    nroEquip = 200,
                    descrEquip = "teste"
                )
            )
        )
        setContent(typeEquip = TypeEquip.VEICULOSEG)
        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").performClick()
        composeTestRule.onNodeWithText("SIM").performClick()
        composeTestRule.onNodeWithText("100").assertIsNotDisplayed()
        composeTestRule.waitUntilTimeout(5_000)
    }

    private fun setContent(
        flowApp: FlowApp = FlowApp.ADD,
        typeEquip: TypeEquip = TypeEquip.VEICULO,
        pos: Int = 0
    ) {
        composeTestRule.setContent {
            EquipSegListScreen(
                viewModel = EquipSegListViewModel(
                    SavedStateHandle(
                        mapOf(
                            Args.FLOW_APP_ARGS to flowApp.ordinal,
                            Args.TYPE_EQUIP_ARGS to typeEquip.ordinal,
                            Args.ID_ARGS to pos,
                        )
                    ),
                    cleanEquipSeg,
                    getEquipSegList,
                    deleteEquipSeg
                ),
                onNavDetalheMovProprio = {},
                onNavNroEquip = {},
                onNavMatricColab = {},
            )
        }
    }

}