package br.com.usinasantafe.pcpcomp.presenter.chave.chavelist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveChave
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocal
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable.SaveLocalTrab
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalTrabRoomModel
import br.com.usinasantafe.pcpcomp.utils.returnDataServerChave
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocal
import br.com.usinasantafe.pcpcomp.utils.returnDataServerLocalTrab
import br.com.usinasantafe.pcpcomp.utils.waitUntilTimeout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ChaveListScreenKtTest: KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val chaveDao: ChaveDao by inject()
    private val localTrabDao: LocalTrabDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(
            generateTestAppComponent(
                server.url("").toString()
            )
        )
    }

    @Test
    fun check_show_screen() =
        runTest {
            setContent()
            composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
            composeTestRule.waitUntilTimeout(3_000)
        }

    @Test
    fun check_show_screen_with_data() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - SALA TI",
                        idLocalTrab = 1
                    )
                )
            )
            localTrabDao.insertAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    )
                )
            )
            setContent()
            composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
            composeTestRule.waitUntilTimeout(3_000)
        }

    @Test
    fun check_show_screen_with_all_list_and_msg_failure() =
        runTest {

            val gson = Gson()

            val saveChave: SaveChave by inject()
            val itemTypeChave = object : TypeToken<List<Chave>>() {}.type
            val chaveList = gson.fromJson<List<Chave>>(returnDataServerChave, itemTypeChave)
            saveChave(chaveList)

            setContent()
            composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
            composeTestRule.waitUntilTimeout(3_000)
        }

    @Test
    fun check_show_screen_with_all_list() =
        runTest {

            val gson = Gson()

            val saveChave: SaveChave by inject()
            val itemTypeChave = object : TypeToken<List<Chave>>() {}.type
            val chaveList = gson.fromJson<List<Chave>>(returnDataServerChave, itemTypeChave)
            saveChave(chaveList)

            val saveLocalTrab: SaveLocalTrab by inject()
            val itemTypeLocalTrab = object : TypeToken<List<LocalTrab>>() {}.type
            val localTrabList = gson.fromJson<List<LocalTrab>>(returnDataServerLocalTrab, itemTypeLocalTrab)
            saveLocalTrab(localTrabList)

            setContent()

            composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
            composeTestRule.waitUntilTimeout(10_000)
        }

    @Test
    fun check_return_field_key() =
        runTest {

            val gson = Gson()

            val saveChave: SaveChave by inject()
            val itemTypeChave = object : TypeToken<List<Chave>>() {}.type
            val chaveList = gson.fromJson<List<Chave>>(returnDataServerChave, itemTypeChave)
            saveChave(chaveList)

            val saveLocalTrab: SaveLocalTrab by inject()
            val itemTypeLocalTrab = object : TypeToken<List<LocalTrab>>() {}.type
            val localTrabList = gson.fromJson<List<LocalTrab>>(returnDataServerLocalTrab, itemTypeLocalTrab)
            saveLocalTrab(localTrabList)

            setContent()

            composeTestRule.waitUntilTimeout(2_000)

            composeTestRule.onNodeWithText("LISTA DE CHAVE").assertIsDisplayed()
            composeTestRule.onNodeWithTag(TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN).performTextInput("TECNOLOGIA")

            composeTestRule.waitUntilTimeout(10_000)
        }

    private fun setContent() {
        composeTestRule.setContent {
            ChaveListScreen(
                viewModel = koinViewModel<ChaveListViewModel>(),
                onNavMenuControleList = {},
                onNavMatricColab = {},
                onNavDetalhe = {}
            )
        }
    }

}