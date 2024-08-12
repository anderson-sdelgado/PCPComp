package br.com.usinasantafe.pcpcomp.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.pcpcomp.presenter.Screens.CONFIG
import br.com.usinasantafe.pcpcomp.presenter.Screens.LOCAL
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_COLAB
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_VIGIA
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_APONT
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_INICIAL
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_VEIC_PROPRIO
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_VIGIA
import br.com.usinasantafe.pcpcomp.presenter.Screens.SENHA
import br.com.usinasantafe.pcpcomp.presenter.Screens.SPLASH
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.ConfigScreen
import br.com.usinasantafe.pcpcomp.presenter.configuration.config.ConfigViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.local.LocalScreen
import br.com.usinasantafe.pcpcomp.presenter.initial.local.LocalViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.MatricVigiaScreen
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.MatricVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.menuapont.MenuApontScreen
import br.com.usinasantafe.pcpcomp.presenter.initial.menuapont.MenuApontViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.SenhaScreen
import br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial.MenuInicialScreen
import br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial.MenuInicialViewModel
import br.com.usinasantafe.pcpcomp.presenter.initial.nomevigia.NomeVigiaScreen
import br.com.usinasantafe.pcpcomp.presenter.initial.nomevigia.NomeVigiaViewModel
import br.com.usinasantafe.pcpcomp.presenter.configuration.senha.SenhaViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.movequip.MovEquipProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.movequip.MovEquipProprioViewModel
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
                viewModel = koinViewModel<LocalViewModel>(),
                onNavNomeVigia = { navActions.navigationToNomeVigia() },
                onNavMenuApont = { navActions.navigationToMenuApont() }
            )
        }
        composable(MENU_APONT) {
            MenuApontScreen(
                viewModel = koinViewModel<MenuApontViewModel>(),
                onNavMovVeicProprio = { navActions.navigationToMovVeicProprio() },
                onNavMovVeicVisitTerc = { navActions.navigationToMovVeicVisitTerc() },
                onNavMovVeicResidencia = { navActions.navigationToMovVeicResidencia() },
                onNavSplashScreen = { navActions.navigationToSplash() }
            )
        }
        composable(MOV_VEIC_PROPRIO) {
            MovEquipProprioScreen(
                viewModel = koinViewModel<MovEquipProprioViewModel>(),
                onNavMatricColab = { navActions.navigationToMatriColab(it) }
            )
        }
        composable(
            "$MATRIC_COLAB/{flowApp}",
            arguments = listOf(navArgument("flowApp") { type = NavType.IntType })
        ){ entry ->
            MatricColabScreen(
                flowApp = entry.arguments?.getInt("flowApp")!!
            )
        }
    }
}