package br.com.usinasantafe.pcpcomp.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.pcpcomp.presenter.Args.CPF_VISIT_TERC_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.CPF_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DESTINO_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DESTINO_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DETALHE_MOV_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DETALHE_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.DETALHE_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.EQUIP_SEG_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NRO_EQUIP_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.PASSAG_COLAB_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.LOCAL_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MATRIC_COLAB_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MATRIC_VIGIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MENU_APONT_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MENU_INICIAL_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOTORISTA_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_EQUIP_PROPRIO_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_EQUIP_RESIDENCIA_EDIT_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_EQUIP_RESIDENCIA_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_EQUIP_VISIT_TERC_EDIT_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.MOV_EQUIP_VISIT_TERC_LIST_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_COLAB_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_VIGIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOME_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.NOTA_FISCAL_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.OBSERV_PROPRIO_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.OBSERV_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.OBSERV_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.PASSAG_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.PLACA_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.PLACA_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.SENHA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.TIPO_VISIT_TERC_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.VEICULO_RESIDENCIA_ROUTE
import br.com.usinasantafe.pcpcomp.presenter.Routes.VEICULO_VISIT_TERC_ROUTE
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
import br.com.usinasantafe.pcpcomp.presenter.proprio.passagcolablist.PassagColabListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe.DetalheMovProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe.DetalheProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist.EquipSegListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.equipseglist.EquipSegListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.NroEquipScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip.NroEquipProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab.MatricColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.movlist.MovEquipProprioListScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.movlist.MovEquipProprioListViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.nomecolab.NomeColabViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal.NotaFiscalProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal.NotaFiscalViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.ObservProprioScreen
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.ObservProprioViewModel
import br.com.usinasantafe.pcpcomp.presenter.proprio.passagcolablist.PassagColabListViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe.DetalheResidenciaScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe.DetalheResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.motorista.MotoristaResidenciaScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.motorista.MotoristaResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist.MovEquipResidenciaEditListScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist.MovEquipResidenciaEditListViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.movlist.MovEquipResidenciaListScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.movlist.MovEquipResidenciaListViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.observ.ObservResidenciaScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.observ.ObservResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.placa.PlacaResidenciaScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.placa.PlacaResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo.VeiculoResidenciaScreen
import br.com.usinasantafe.pcpcomp.presenter.residencia.veiculo.VeiculoResidenciaViewModel
import br.com.usinasantafe.pcpcomp.presenter.splash.SplashScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf.CpfVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf.CpfVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.destino.DestinoVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.destino.DestinoVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe.DetalheVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe.DetalheVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.moveditlist.MovEquipVisitTercEditListScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.moveditlist.MovEquipVisitTercEditListViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.movlist.MovEquipVisitTercListScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.movlist.MovEquipVisitTercListViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.nome.NomeVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.nome.NomeVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.observ.ObservVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.observ.ObservVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist.PassagVisitTercListScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist.PassagVisitTercListViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.placa.PlacaVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.placa.PlacaVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo.TipoVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo.TipoVisitTercViewModel
import br.com.usinasantafe.pcpcomp.presenter.visitterc.veiculo.VeiculoVisitTercScreen
import br.com.usinasantafe.pcpcomp.presenter.visitterc.veiculo.VeiculoVisitTercViewModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.TypeMov
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
                    navActions.navigationToDetalheProprio(it)
                },
                onNavMenuApont = { navActions.navigationToMenuApont() },
                onNavSplashScreen = { navActions.navigationToSplash() }
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
                    navActions.navigationToDetalheProprio(
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
                    navActions.navigationToDestinoProprio(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscalProprio(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservProprio(
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
                onNavDetalheProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMatricPassag = {
                    navActions.navigationToMatricColab(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoProprio(
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
                    navActions.navigationToDetalheProprio(
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
                    navActions.navigationToDetalheProprio(
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
                onNavPassagList = {
                    navActions.navigationToPassagColabList(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNotaFiscal = {
                    navActions.navigationToNotaFiscalProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservProprio(
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
                    navActions.navigationToDestinoProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
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
                    navActions.navigationToDestinoProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavNotaFiscalProprio = {
                    navActions.navigationToNotaFiscalProprio(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMovEquipProprioList = { navActions.navigationToMovEquipProprioList() },
            )
        }
        composable(
            MOV_EQUIP_VISIT_TERC_LIST_ROUTE
        ) {
            MovEquipVisitTercListScreen(
                viewModel = koinViewModel<MovEquipVisitTercListViewModel>(),
                onNavVeiculo = {
                    navActions.navigationToVeiculoVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipVisitTercEditList()
                },
                onNavMenuApont = { navActions.navigationToMenuApont() },
                onNavObserv = {
                    navActions.navigationToObservVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMov.OUTPUT.ordinal,
                        id = it
                    )
                },
            )
        }
        composable(
            VEICULO_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            VeiculoVisitTercScreen(
                viewModel = koinViewModel<VeiculoVisitTercViewModel>(),
                onNavPlacaVisitTerc = {},
                onNavMovEquipVisitTercList = {
                    navActions.navigationToMovEquipVisitTercList()
                },
                onNavDetalheVisitTerc = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }
        composable(
            PLACA_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PlacaVisitTercScreen(
                viewModel = koinViewModel<PlacaVisitTercViewModel>(),
                onNavTipoVisitTerc = {
                    navActions.navigationToTipoVisitTerc()
                },
                onNavVeiculoVisitTerc = {
                    navActions.navigationToVeiculoVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavDetalheVisitTerc = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                }
            )
        }
        composable(
            TIPO_VISIT_TERC_ROUTE
        ) {
            TipoVisitTercScreen(
                viewModel = koinViewModel<TipoVisitTercViewModel>(),
                onNavPlacaVisitTerc = {
                    navActions.navigationToPlacaVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavCpfVisitTerc = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = FlowApp.ADD.ordinal,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = 0
                    )
                }
            )
        }
        composable(
            CPF_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            CpfVisitTercScreen(
                viewModel = koinViewModel<CpfVisitTercViewModel>(),
                onNavTipoVisitTerc = {
                    navActions.navigationToTipoVisitTerc()
                },
                onNavDetalheVisitTerc = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavNomeVisitTerc = {
                    navActions.navigationToNomeVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                        cpfVisitTerc = it
                    )
                }
            )
        }
        composable(
            NOME_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
                navArgument(CPF_VISIT_TERC_ARGS) { type = NavType.StringType }
            )
        ) { entry ->
            NomeVisitTercScreen(
                viewModel = koinViewModel<NomeVisitTercViewModel>(),
                onNavCpfVisitTerc = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                    )
                },
                onNavPassagVisitTerc = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!,
                    )
                }
            )
        }
        composable(
            PASSAG_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_OCUPANTE_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PassagVisitTercListScreen(
                viewModel = koinViewModel<PassagVisitTercListViewModel>(),
                onNavCpfMotorista = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = entry.arguments?.getInt(TYPE_OCUPANTE_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheVisitTerc = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavCpfPassag = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
            )
        }
        composable(
            DESTINO_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            DestinoVisitTercScreen(
                viewModel = koinViewModel<DestinoVisitTercViewModel>(),
                onNavPassagList = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                },
                onNavDetalheMovProprio = {
                    navActions.navigationToDetalheProprio(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        typeMov = TypeMov.INPUT.ordinal,
                        id = entry.arguments?.getInt(ID_ARGS)!!
                    )
                }
            )
        }
        composable(
            OBSERV_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservVisitTercScreen(
                viewModel = koinViewModel<ObservVisitTercViewModel>(),
                onNavDestino = {
                    navActions.navigationToDestinoVisitTerc(
                        flowApp = entry.arguments?.getInt(FLOW_APP_ARGS)!!,
                        id = 0
                    )
                },
                onNavMovEquipList = {
                    navActions.navigationToMovEquipVisitTercList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            MOV_EQUIP_VISIT_TERC_EDIT_ROUTE
        ) {
            MovEquipVisitTercEditListScreen(
                viewModel = koinViewModel<MovEquipVisitTercEditListViewModel>(),
                onNavMovEquipList = {
                    navActions.navigationToMovEquipVisitTercList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheVisitTerc(
                        id = it
                    )
                }
            )
        }
        composable(
            DETALHE_VISIT_TERC_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheVisitTercScreen(
                viewModel = koinViewModel<DetalheVisitTercViewModel>(),
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipVisitTercEditList()
                },
                onNavVeiculo = {
                    navActions.navigationToVeiculoVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPlaca = {
                    navActions.navigationToPlacaVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavCpf = {
                    navActions.navigationToCpfVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.MOTORISTA.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPassagList = {
                    navActions.navigationToPassagVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeOcupante = TypeOcupante.PASSAGEIRO.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavDestino = {
                    navActions.navigationToDestinoVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservVisitTerc(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMov.OUTPUT.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            MOV_EQUIP_RESIDENCIA_LIST_ROUTE
        ) {
            MovEquipResidenciaListScreen(
                viewModel = koinViewModel<MovEquipResidenciaListViewModel>(),
                onNavVeiculo = {
                    navActions.navigationToVeiculoResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipResidenciaEditList()
                },
                onNavObserv = {
                    navActions.navigationToObservResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMov.INPUT.ordinal,
                        id = 0
                    )
                },
                onNavMenuApont = {
                    navActions.navigationToMenuApont()
                },
            )
        }
        composable(
            VEICULO_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            VeiculoResidenciaScreen(
                viewModel = koinViewModel<VeiculoResidenciaViewModel>(),
                onNavMovEquipList = {
                    navActions.navigationToMovEquipResidenciaList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPlaca = {
                    navActions.navigationToPlacaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            PLACA_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            PlacaResidenciaScreen(
                viewModel = koinViewModel<PlacaResidenciaViewModel>(),
                onNavVeiculo = {
                    navActions.navigationToVeiculoResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMotorista = {
                    navActions.navigationToMotoristaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
            )
        }
        composable(
            MOTORISTA_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            MotoristaResidenciaScreen(
                viewModel = koinViewModel<MotoristaResidenciaViewModel>(),
                onNavPlaca = {
                    navActions.navigationToPlacaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        typeMov = TypeMov.INPUT.ordinal,
                        id = 0
                    )
                },
            )
        }
        composable(
            OBSERV_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(FLOW_APP_ARGS) { type = NavType.IntType },
                navArgument(TYPE_MOV_ARGS) { type = NavType.IntType },
                navArgument(ID_ARGS) { type = NavType.IntType }
            )
        ) { entry ->
            ObservResidenciaScreen(
                viewModel = koinViewModel<ObservResidenciaViewModel>(),
                onNavMotorista = {
                    navActions.navigationToMotoristaResidencia(
                        flowApp = FlowApp.ADD.ordinal,
                        id = 0
                    )
                },
                onNavMovEquipList = {
                    navActions.navigationToMovEquipResidenciaList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
        composable(
            MOV_EQUIP_RESIDENCIA_EDIT_LIST_ROUTE
        ) {
            MovEquipResidenciaEditListScreen(
                viewModel = koinViewModel<MovEquipResidenciaEditListViewModel>(),
                onNavMovEquipList = {
                    navActions.navigationToMovEquipResidenciaList()
                },
                onNavDetalhe = {
                    navActions.navigationToDetalheResidencia(
                        id = it
                    )
                },
            )
        }
        composable(
            DETALHE_RESIDENCIA_ROUTE,
            arguments = listOf(
                navArgument(ID_ARGS) { type = NavType.IntType },
            )
        ) { entry ->
            DetalheResidenciaScreen(
                viewModel = koinViewModel<DetalheResidenciaViewModel>(),
                onNavMovEquipEditList = {
                    navActions.navigationToMovEquipResidenciaEditList()
                },
                onNavVeiculo = {
                    navActions.navigationToVeiculoResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavPlaca = {
                    navActions.navigationToPlacaResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavMotorista = {
                    navActions.navigationToMotoristaResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
                onNavObserv = {
                    navActions.navigationToObservResidencia(
                        flowApp = FlowApp.CHANGE.ordinal,
                        typeMov = TypeMov.INPUT.ordinal,
                        id = entry.arguments?.getInt(
                            ID_ARGS
                        )!!
                    )
                },
            )
        }
    }
}