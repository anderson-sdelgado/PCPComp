package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveRLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveVisitante
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.proprio.destino.TAG_DESTINO_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.TAG_OBSERV_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import br.com.usinasantafe.pcpcomp.utils.dispatcherSuccessFunctional
import br.com.usinasantafe.pcpcomp.utils.returnDataServerColab
import br.com.usinasantafe.pcpcomp.utils.returnDataServerEquip
import br.com.usinasantafe.pcpcomp.utils.returnDataServerFluxo
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocal
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

class MovProprioFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mov_proprio_flow() = runTest {

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

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("19035 - JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Observação")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("INSERIR").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("OK").performClick()

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
                idLocal = 1,
            )
        )

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

