package br.com.usinasantafe.pcpcomp.presenter.config

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.usinasantafe.pcpcomp.presenter.NavigationGraph
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConfigNavigTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationGraph(navHostController = navController, startDestination = "config")
        }
    }

    @Test
    fun verify_onStartDestinationIsConfigScreen() {
        composeTestRule.onNodeWithText("CANCELAR")
            .performClick()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "menuinicial")
    }

}