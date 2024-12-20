package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocalTrab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveVisitante
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.chave.chavelist.TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.chave.observ.TAG_OBSERV_TEXT_FIELD_CHAVE
import br.com.usinasantafe.pcpcomp.ui.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.dispatcherSuccessFunctional
import br.com.usinasantafe.pcpcomp.utils.returnDataServerChave
import br.com.usinasantafe.pcpcomp.utils.returnDataServerColab
import br.com.usinasantafe.pcpcomp.utils.returnDataServerEquip
import br.com.usinasantafe.pcpcomp.utils.returnDataServerFluxo
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocal
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocalTrab
import br.com.usinasantafe.pcpcomp.utils.returnDataServerRLocalFluxo
import br.com.usinasantafe.pcpcomp.utils.returnDataServerTerceiro
import br.com.usinasantafe.pcpcomp.utils.returnDataServerVisitante
import br.com.usinasantafe.pcpcomp.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.Date

class MovChaveFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mov_chave_flow() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher =
            dispatcherSuccessFunctional
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )

        initialRegister()

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE").assertIsDisplayed()
        composeTestRule.onNodeWithText("RETIRADA DE CHAVE").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN).performTextInput("TECNOLOGIA")
        composeTestRule.onNodeWithTag("item_list_82").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextInput("TESTE OBSERV")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE DE CHAVE").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("RONALDO GOMES CARLOS").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextInput("TESTE RETORNO")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.waitUntilTimeout(5_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.waitUntilTimeout(5_000)

        composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN).performTextInput("TECNOLOGIA")
        composeTestRule.onNodeWithTag("item_list_81").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_2").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MATRICULA COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

//        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("RONALDO GOMES CARLOS").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithTag("item_list_3").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextClearance()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_CHAVE).performTextInput("TESTE RETORNO")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOVIMENTO").performClick()
        composeTestRule.onNodeWithText("FECHAR").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("FECHAR MOVIMENTO(S)").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK).performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        composeTestRule.waitUntilTimeout(3_000)

    }

    private suspend fun initialRegister() {

        val gson = Gson()

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = "1.0",
                idBD = 1,
                flagUpdate = FlagUpdate.UPDATED,
                matricVigia = 19759,
                idLocal = 4,
            )
        )

        val saveChave: SaveChave by inject()
        val itemTypeChave = object : TypeToken<List<Chave>>() {}.type
        val chaveList = gson.fromJson<List<Chave>>(returnDataServerChave, itemTypeChave)
        saveChave(chaveList)

        val saveColab: SaveColab by inject()
        val itemTypeColab = object : TypeToken<List<Colab>>() {}.type
        val colabList = gson.fromJson<List<Colab>>(returnDataServerColab, itemTypeColab)
        saveColab(colabList)

        val saveEquip: SaveEquip by inject()
        val itemTypeEquip = object : TypeToken<List<Equip>>() {}.type
        val equipList = gson.fromJson<List<Equip>>(returnDataServerEquip, itemTypeEquip)
        saveEquip(equipList)

        val saveFluxo: SaveFluxo by inject()
        val itemTypeFluxo = object : TypeToken<List<Fluxo>>() {}.type
        val fluxoList = gson.fromJson<List<Fluxo>>(returnDataServerFluxo, itemTypeFluxo)
        saveFluxo(fluxoList)

        val saveLocal: SaveLocal by inject()
        val itemTypeLocal = object : TypeToken<List<Local>>() {}.type
        val localList = gson.fromJson<List<Local>>(returnDataServerLocal, itemTypeLocal)
        saveLocal(localList)

        val saveLocalTrab: SaveLocalTrab by inject()
        val itemTypeLocalTrab = object : TypeToken<List<LocalTrab>>() {}.type
        val localTrabList = gson.fromJson<List<LocalTrab>>(returnDataServerLocalTrab, itemTypeLocalTrab)
        saveLocalTrab(localTrabList)

        val saveRLocalFluxo: SaveRLocalFluxo by inject()
        val itemTypeRLocalFluxo = object : TypeToken<List<RLocalFluxo>>() {}.type
        val rLocalFluxoList = gson.fromJson<List<RLocalFluxo>>(returnDataServerRLocalFluxo, itemTypeRLocalFluxo)
        saveRLocalFluxo(rLocalFluxoList)

        val saveTerceiro: SaveTerceiro by inject()
        val itemTypeTerceiro = object : TypeToken<List<Terceiro>>() {}.type
        val terceiroList = gson.fromJson<List<Terceiro>>(returnDataServerTerceiro, itemTypeTerceiro)
        saveTerceiro(terceiroList)

        val saveVisitante: SaveVisitante by inject()
        val itemTypeVisitante = object : TypeToken<List<Visitante>>() {}.type
        val visitanteList =
            gson.fromJson<List<Visitante>>(returnDataServerVisitante, itemTypeVisitante)
        saveVisitante(visitanteList)

        val movEquipProprioDao: MovEquipProprioDao by inject()
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = Date().time,
                idEquipMovEquipProprio = 300,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENT
            )
        )

    }
}