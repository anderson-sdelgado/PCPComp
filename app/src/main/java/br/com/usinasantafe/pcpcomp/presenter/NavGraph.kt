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
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DESTINO_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DETALHE_MOV_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.EQUIP_SEG_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NRO_EQUIP_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.PASSAG_COLAB_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.LOCAL_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MATRIC_COLAB_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MATRIC_VIGIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MENU_APONT_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MENU_INICIAL_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_EQUIP_PROPRIO_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_COLAB_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_VIGIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOTA_FISCAL_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.OBSERV_PROPRIO_ROUTE
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
import br.com.usinasantafe.pcpcomp.presenter.proprio.destino.DestinoProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.destino.DestinoProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist.PassagColabListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe.DetalheMovProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe.DetalheProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist.EquipSegListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist.EquipSegListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.NroEquipScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.NroEquipProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.movpropriolist.MovEquipProprioListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.movpropriolist.MovEquipProprioListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal.NotaFiscalProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal.NotaFiscalViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.ObservProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.ObservProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist.PassagColabListViewModel
import br.com.usinasantafe.pcpcomp.presenter.splash.SplashScreen
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
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
                onNavMovVeicProprio = { navActions.navigationToMovEquipProprioList() },
                onNavMovVeicVisitTerc = { navActions.navigationToMovEquipVisitTercList() },
                onNavMovVeicResidencia = { navActions.navigationToMovEquipResidenciaList() },
                onNavSplashScreen = { navActions.navigationToSplash() }
            )
        }
        composable(MOV_EQUIP_PROPRIO_LIST_ROUTE) {
            MovEquipProprioListScreen(
                viewModel = koinViewModel<MovEquipProprioListViewModel>(),
                onNavNroEquip = {
                    navActions.navigationToNroEquip(
                        flowApp = FlowApp.ADD.ordinal,
                        typeEquip = TypeEquip.VEICULO.ordinal,
                        id = 0
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheMovProprio(it)
                }
            )
        }
        composable(
            MATRIC_COLAB_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            MatricColabScreen(
                viewModel = koinViewModel<MatricColabViewModel>(),
                onNavMovVeicProprio = { navActions.navigationToMovEquipProprioList() },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNomeColab = {
                    navActions.navigationToNomeColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!,
                        matricColab = it
                    )
                }
            )
        }
        composable(
            NOME_COLAB_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
                navArgument(MATRIC_COLAB_ARGS) { type = NavType.StringType }
            )
        ) { entry ->
            NomeColabScreen(
                viewModel = koinViewModel<NomeColabViewModel>(),
                onNavMatricColab = {
                    navActions.navigationToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavPassagColabList = {
                    navActions.navigationToPassagColabList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            DETALHE_MOV_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheMovProprioScreen(
                viewModel = koinViewModel<DetalheProprioViewModel>(),
                onNavMovProprioList = { navActions.navigationToMovEquipProprioList() },
                onNavNroEquip = {
                    navActions.navigationToNroEquip(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeEquip = TypeEquip.VEICULO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavEquipSegList = {
                    navActions.navigationToEquipSegList(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeEquip = TypeEquip.VEICULOSEG.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigationToMatricColab(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavPassagList = {
                    navActions.navigationToPassagColabList(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestino(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscal(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObserv(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            PASSAG_COLAB_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PassagColabListScreen(
                viewModel = koinViewModel<PassagColabListViewModel>(),
                onNavMatricMotorista = {
                    navActions.navigationToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricPassag = {
                    navActions.navigationToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = it,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestino(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            NRO_EQUIP_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_EQUIP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            NroEquipScreen(
                viewModel = koinViewModel<NroEquipProprioViewModel>(),
                onNavMovProprioList = {
                    navActions.navigationToMovEquipProprioList()
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavEquipSegList = {
                    navActions.navigationToEquipSegList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeEquip = entry.arguments?.getInt(TYPE_EQUIP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            EQUIP_SEG_LIST_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_EQUIP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            EquipSegListScreen(
                viewModel = koinViewModel<EquipSegListViewModel>(),
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNroEquip = {
                    navActions.navigationToNroEquip(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeEquip = it,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavMatricColab = {
                    navActions.navigationToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            DESTINO_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            DestinoProprioScreen(
                viewModel = koinViewModel<DestinoProprioViewModel>(),
                onNavEquipSegList = {
                    navActions.navigationToEquipSegList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeEquip = TypeEquip.VEICULOSEG.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscal(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObserv(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            NOTA_FISCAL_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            NotaFiscalProprioScreen(
                viewModel = koinViewModel<NotaFiscalViewModel>(),
                onNavDestino = {
                    navActions.navigationToDestino(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObserv(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            OBSERV_PROPRIO_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservProprioScreen(
                viewModel = koinViewModel<ObservProprioViewModel>(),
                onNavDestinoProprio = {
                    navActions.navigationToDestino(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavNotaFiscalProprio = {
                    navActions.navigationToNotaFiscal(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheMovProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMovEquipProprioList = { navActions.navigationToMovEquipProprioList() },
            )
        }
    }
}