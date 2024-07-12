package br.com.usinasantafe.pcpcomp.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.usinasantafe.pcpcomp.presenter.Screens.CONFIG
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_INICIAL
import br.com.usinasantafe.pcpcomp.presenter.Screens.SENHA
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigScreen
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigViewModel
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaScreen
import br.com.usinasantafe.pcpcomp.presenter.menuinicial.MenuInicialScreen
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = MENU_INICIAL,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(MENU_INICIAL) {
            MenuInicialScreen(onNavSenha = { navActions.navigationToSenha() })
        }
        composable(SENHA) {
            SenhaScreen(
                viewModel = koinViewModel<SenhaViewModel>(),
                onNavMenuInicial = { navActions.navigationToMenuInicial() },
                onNavConfig = { navActions.navigationToConfig() }
            )
        }
        composable(CONFIG) {
            ConfigScreen(
                viewModel = koinViewModel<ConfigViewModel>(),
                onNavMenuInicial = { navActions.navigationToMenuInicial() }
            )
        }
    }
}