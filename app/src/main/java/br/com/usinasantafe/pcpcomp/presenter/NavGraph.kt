package br.com.usinasantafe.pcpcomp.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.POS_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DETALHE_MOV_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.PASSAG_COLAB_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.LOCAL_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MATRIC_COLAB_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MATRIC_VIGIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MENU_APONT_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MENU_INICIAL_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_COLAB_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_VIGIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.SENHA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.SPLASH_ROUTE
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
import br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist.PassagColabListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhemovproprio.DetalheMovProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.movproprio.MovEquipProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.movproprio.MovEquipProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist.PassagColabListViewModel
import br.com.usinasantafe.pcpcomp.presenter.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(SPLASH_ROUTE) {
            SplashScreen(
                onNavMenuInicial = { navActions.navigationToMenuInicial() },
            )
        }
        composable(MENU_INICIAL_ROUTE) {
            MenuInicialScreen(
                viewModel = koinViewModel<MenuInicialViewModel>(),
                onNavMatricVigia = { navActions.navigationToMatricVigia() },
                onNavSenha = { navActions.navigationToSenha() }
            )
        }
        composable(SENHA_ROUTE) {
            SenhaScreen(
                viewModel = koinViewModel<SenhaViewModel>(),
                onNavMenuInicial = { navActions.navigationToMenuInicial() },
                onNavConfig = { navActions.navigationToConfig() }
            )
        }
        composable(CONFIG_ROUTE) {
            ConfigScreen(
                viewModel = koinViewModel<ConfigViewModel>(),
                onNavMenuInicial = { navActions.navigationToMenuInicial() }
            )
        }
        composable(MATRIC_VIGIA_ROUTE) {
            MatricVigiaScreen(
                viewModel = koinViewModel<MatricVigiaViewModel>(),
                onNavMenuInicial = { navActions.navigationToMenuInicial() },
                onNavNomeVigia = { navActions.navigationToNomeVigia() }
            )
        }
        composable(NOME_VIGIA_ROUTE) {
            NomeVigiaScreen(
                viewModel = koinViewModel<NomeVigiaViewModel>(),
                onNavMatricVigia = { navActions.navigationToMatricVigia() },
                onNavLocal = { navActions.navigationToLocal() }
            )
        }
        composable(LOCAL_ROUTE) {
            LocalScreen(
                viewModel = koinViewModel<LocalViewModel>(),
                onNavNomeVigia = { navActions.navigationToNomeVigia() },
                onNavMenuApont = { navActions.navigationToMenuApont() }
            )
        }
        composable(MENU_APONT_ROUTE) {
            MenuApontScreen(
                viewModel = koinViewModel<MenuApontViewModel>(),
                onNavMovVeicProprio = { navActions.navigationToMovProprio() },
                onNavMovVeicVisitTerc = { navActions.navigationToMovVisitTerc() },
                onNavMovVeicResidencia = { navActions.navigationToMovResidencia() },
                onNavSplashScreen = { navActions.navigationToSplash() }
            )
        }
        composable(MOV_PROPRIO_ROUTE) {
            MovEquipProprioScreen(
                viewModel = koinViewModel<MovEquipProprioViewModel>(),
                onNavMatricColab = { flowApp, typeEquip, pos ->
                    navActions.navigationToMatricColab(
                        flowApp,
                        typeEquip,
                        pos
                    )
                }
            )
        }
        composable(
            MATRIC_COLAB_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(POS_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            MatricColabScreen(
                viewModel = koinViewModel<MatricColabViewModel>(),
                onNavMovVeicProprio = { navActions.navigationToMovProprio() },
                onNavDetalheMovProprio = { navActions.navigationToDetalheMovProprio() },
                onNavNomeColab = {
                    navActions.navigationToNomeColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        pos = entry.arguments?.getInt(POS_ARGS)!!,
                        it
                    )
                }
            )
        }
        composable(
            NOME_COLAB_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(POS_ARGS) { type = NavType.IntType },
                navArgument(MATRIC_COLAB_ARGS) { type = NavType.StringType}
            )
        ) { entry ->
            NomeColabScreen(
                viewModel = koinViewModel<NomeColabViewModel>(),
                onNavMatricColab = {
                    navActions.navigationToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        pos = entry.arguments?.getInt(POS_ARGS)!!
                    )
                },
                onNavPassagColabList = {
                    navActions.navigationToPassagColabList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        pos = entry.arguments?.getInt(POS_ARGS)!!
                    )
                }
            )
        }
        composable(DETALHE_MOV_PROPRIO_ROUTE) {
            DetalheMovProprioScreen()
        }
        composable(PASSAG_COLAB_LIST_ROUTE) {
            PassagColabListScreen(
                viewModel = koinViewModel<PassagColabListViewModel>()
            )
        }
    }
}