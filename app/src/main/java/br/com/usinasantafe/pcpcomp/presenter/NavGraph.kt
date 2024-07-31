package br.com.usinasantafe.pcpcomp.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.usinasantafe.pcpcomp.presenter.Screens.CONFIG
import br.com.usinasantafe.pcpcomp.presenter.Screens.LOCAL
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_VIGIA
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_INICIAL
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_VIGIA
import br.com.usinasantafe.pcpcomp.presenter.Screens.SENHA
import br.com.usinasantafe.pcpcomp.presenter.Screens.SPLASH
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigScreen
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigViewModel
import br.com.usinasantafe.pcpcomp.presenter.local.LocalScreen
import br.com.usinasantafe.pcpcomp.presenter.matricvigia.MatricVigiaScreen
import br.com.usinasantafe.pcpcomp.presenter.matricvigia.MatricVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaScreen
import br.com.usinasantafe.pcpcomp.presenter.menuinicial.MenuInicialScreen
import br.com.usinasantafe.pcpcomp.presenter.menuinicial.MenuInicialViewModel
import br.com.usinasantafe.pcpcomp.presenter.nomevigia.NomeVigiaScreen
import br.com.usinasantafe.pcpcomp.presenter.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaViewModel
import br.com.usinasantafe.pcpcomp.presenter.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(SPLASH) {
            SplashScreen(
                onNavMenuInicial = { navActions.navigationToMenuInicial() },
            )
        }
        composable(MENU_INICIAL) {
            MenuInicialScreen(
                viewModel = koinViewModel<MenuInicialViewModel>(),
                onNavMatricVigia = { navActions.navigationToMatricVigia() },
                onNavSenha = { navActions.navigationToSenha() }
            )
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
        composable(MATRIC_VIGIA) {
            MatricVigiaScreen(
                viewModel = koinViewModel<MatricVigiaViewModel>(),
                onNavMenuInicial = { navActions.navigationToMenuInicial() },
                onNavNomeVigia = { navActions.navigationToNomeVigia() }
            )
        }
        composable(NOME_VIGIA) {
            NomeVigiaScreen(
                viewModel = koinViewModel<NomeVigiaViewModel>(),
                onNavMatricVigia = { navActions.navigationToMatricVigia() },
                onNavLocal = { navActions.navigationToLocal() }
            )
        }
        composable(LOCAL) {
            LocalScreen(
            )
        }
    }
}