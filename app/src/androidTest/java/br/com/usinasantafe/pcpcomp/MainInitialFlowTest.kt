package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.resultTokenRetrofit
import br.com.usinasantafe.pcpcomp.presenter.proprio.destino.TAG_DESTINO_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.TAG_OBSERV_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.returnDataSendMovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.returnDataServerColab
import br.com.usinasantafe.pcpcomp.utils.returnDataServerEquip
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocal
import br.com.usinasantafe.pcpcomp.utils.returnDataServerTerceiro
import br.com.usinasantafe.pcpcomp.utils.returnDataServerVisitante
import br.com.usinasantafe.pcpcomp.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

class FlowMainInitialFuncionalTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verify_flow_main_initial() = runTest {

        val server = MockWebServer()
        server.start()
        server.dispatcher = dispatcherSuccessFunctional
        loadKoinModules(generateTestAppComponent(server.url("").toString()))

        initialRegister()

        composeTestRule.onNodeWithText("APONTAMENTO").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONFIGURAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithText("SAIR").assertIsDisplayed()

        composeTestRule.onNodeWithText("APONTAMENTO")
            .performClick()

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("NOME VIGIA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("LOCAL").assertIsDisplayed()
        composeTestRule.onNodeWithText("1 - USINA").performClick()

        composeTestRule.onNodeWithText("MENU").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("MOV. VEÍCULO PRÓPRIO").performClick()

        composeTestRule.onNodeWithText("CONTROLE VEÍCULO USINA").assertIsDisplayed()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("VEÍCULO(S) SECUNDÁRIO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("7").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("ANDERSON DA SILVA DELGADO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("INSERIR").performClick()

        composeTestRule.onNodeWithText("1").performClick()
        composeTestRule.onNodeWithText("9").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("NOME COLABORADOR").assertIsDisplayed()
        composeTestRule.onNodeWithText("JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("PASSAGEIRO(S)").assertIsDisplayed()
        composeTestRule.onNodeWithText("19035 - JOSE DONIZETE ALVES DA SILVA").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("DESTINO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_DESTINO_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Destino")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_PROPRIO)
            .performTextInput("Teste Observação")
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()
        composeTestRule.onNodeWithText("APAGAR").performClick()

        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithTag("item_list_2").performClick()

        composeTestRule.onNodeWithTag("item_list_1").performClick()
        composeTestRule.onNodeWithText("SIM").performClick()

        composeTestRule.onNodeWithText("INSERIR").performClick()

        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(10_000)

    }

    private suspend fun initialRegister() {

        val gson = Gson()

        val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
        configSharedPreferences.save(
            Config(
                password = "12345",
                number = 16997417840,
                version = "6.00",
                idBD = 1,
                flagUpdate = FlagUpdate.UPDATED,
            )
        )

        val saveAllColab: SaveAllColab by inject()
        val itemTypeColab = object : TypeToken<List<Colab>>() {}.type
        val colabList = gson.fromJson<List<Colab>>(returnDataServerColab, itemTypeColab)
        saveAllColab(colabList)

        val saveAllEquip: SaveAllEquip by inject()
        val itemTypeEquip = object : TypeToken<List<Equip>>() {}.type
        val equipList = gson.fromJson<List<Equip>>(returnDataServerEquip, itemTypeEquip)
        saveAllEquip(equipList)

        val saveAllLocal: SaveAllLocal by inject()
        val itemTypeLocal = object : TypeToken<List<Local>>() {}.type
        val localList = gson.fromJson<List<Local>>(returnDataServerLocal, itemTypeLocal)
        saveAllLocal(localList)

        val saveAllTerceiro: SaveAllTerceiro by inject()
        val itemTypeTerceiro = object : TypeToken<List<Terceiro>>() {}.type
        val terceiroList = gson.fromJson<List<Terceiro>>(returnDataServerTerceiro, itemTypeTerceiro)
        saveAllTerceiro(terceiroList)

        val saveAllVisitante: SaveAllVisitante by inject()
        val itemTypeVisitante = object : TypeToken<List<Visitante>>() {}.type
        val visitanteList =
            gson.fromJson<List<Visitante>>(returnDataServerVisitante, itemTypeVisitante)
        saveAllVisitante(visitanteList)

    }

}

val dispatcherSuccessFunctional: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(returnDataServerColab)
            "/equip.php" -> return MockResponse().setBody(returnDataServerEquip)
            "/local.php" -> return MockResponse().setBody(returnDataServerLocal)
            "/terceiro.php" -> return MockResponse().setBody(returnDataServerTerceiro)
            "/visitante.php" -> return MockResponse().setBody(returnDataServerVisitante)
            "/save-mov-equip-proprio.php" -> return MockResponse().setBody(
                returnDataSendMovEquipProprio
            )
        }
        return MockResponse().setResponseCode(404)
    }
}

