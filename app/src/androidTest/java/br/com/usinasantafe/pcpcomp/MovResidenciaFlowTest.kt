package br.com.usinasantafe.pcpcomp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllEquip
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllVisitante
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.presenter.MainActivity
import br.com.usinasantafe.pcpcomp.presenter.residencia.motorista.TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcpcomp.presenter.residencia.observ.TAG_OBSERV_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcpcomp.presenter.residencia.placa.TAG_PLACA_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo.TAG_VEICULO_TEXT_FIELD_RESIDENCIA
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate
import br.com.usinasantafe.pcpcomp.utils.dispatcherSuccessFunctional
import br.com.usinasantafe.pcpcomp.utils.returnDataServerColab
import br.com.usinasantafe.pcpcomp.utils.returnDataServerEquip
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocal
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

class MovResidenciaFlowTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mov_residencia_flow() = runTest {

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

        composeTestRule.onNodeWithText("MOV. VEÍCULO RESIDÊNCIA").performClick()
        composeTestRule.onNodeWithText("VIGIA: 19759 - ANDERSON DA SILVA DELGADO")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("ENTRADA").performClick()

        composeTestRule.onNodeWithText("VEÍCULO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Gol")

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("PLACA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_PLACA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("abc1234")

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("MOTORISTA").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_MOTORISTA_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Anderson")

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.onNodeWithText("OBSERVAÇÃO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_OBSERV_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Teste")

        composeTestRule.onNodeWithText("OK").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("EDITAR MOVIMENTO(S)").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithTag("item_list_1").performClick()

        composeTestRule.waitUntilTimeout(3_000)

        composeTestRule.onNodeWithText("VEÍCULO").assertIsDisplayed()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextClearance()
        composeTestRule.onNodeWithTag(TAG_VEICULO_TEXT_FIELD_RESIDENCIA)
            .performTextInput("Gol Teste")

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
                version = "5.00",
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