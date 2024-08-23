package br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.NavigationGraph
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_COLAB_SCREEN
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules

class MatricColabNavTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_click_button_cancel_return_menu_initial_screen() {
        setContent()
        navController.popBackStack()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "menuinicial")
    }


    private fun setContent() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationGraph(navHostController = navController, startDestination = "$MATRIC_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}")
        }
    }
}