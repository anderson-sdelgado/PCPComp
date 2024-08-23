package br.com.usinasantafe.pcpcomp.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DESTINO_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DETALHE_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NRO_EQUIP_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.EQUIP_SEG_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.PASSAG_COLAB_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.LOCAL_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_COLAB_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_VIGIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_APONT_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_INICIAL_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_PROPRIO_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_RESIDENCIA_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_VISIT_TERC_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_COLAB_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_VIGIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOTA_FISCAL_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.OBSERV_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.SENHA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.SPLASH_SCREEN

object Screens {
    const val MENU_INICIAL_SCREEN = "menuinicial"
    const val SENHA_SCREEN = "senha"
    const val CONFIG_SCREEN = "config"
    const val MATRIC_VIGIA_SCREEN = "matricvigia"
    const val SPLASH_SCREEN = "splash"
    const val NOME_VIGIA_SCREEN = "nomevigia"
    const val LOCAL_SCREEN = "local"
    const val MENU_APONT_SCREEN = "menuapont"
    const val MOV_EQUIP_PROPRIO_LIST_SCREEN = "movequippropriolist"
    const val MATRIC_COLAB_SCREEN = "matriccolab"
    const val NOME_COLAB_SCREEN = "nomecolab"
    const val MOV_EQUIP_VISIT_TERC_LIST_SCREEN = "movequipvisitterclist"
    const val MOV_EQUIP_RESIDENCIA_LIST_SCREEN = "movequipresidencialist"
    const val DETALHE_PROPRIO_SCREEN = "detalheproprio"
    const val PASSAG_COLAB_LIST_SCREEN = "passagcolablist"
    const val NRO_EQUIP_PROPRIO_SCREEN = "equipproprio"
    const val EQUIP_SEG_LIST_SCREEN = "equipseglist"
    const val DESTINO_PROPRIO_SCREEN = "destinoproprio"
    const val NOTA_FISCAL_PROPRIO_SCREEN = "notafiscalproprio"
    const val OBSERV_PROPRIO_SCREEN = "observproprio"
}

object Args {
    const val FLOW_APP_ARGS = "flowApp"
    const val TYPE_OCUPANTE_ARGS = "typeOcupante"
    const val TYPE_EQUIP_ARGS = "typeEquip"
    const val ID_ARGS = "id"
    const val MATRIC_COLAB_ARGS = "matricColab"
}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val MENU_INICIAL_ROUTE = MENU_INICIAL_SCREEN
    const val SENHA_ROUTE = SENHA_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val MATRIC_VIGIA_ROUTE = MATRIC_VIGIA_SCREEN
    const val NOME_VIGIA_ROUTE = NOME_VIGIA_SCREEN
    const val LOCAL_ROUTE = LOCAL_SCREEN
    const val MENU_APONT_ROUTE = MENU_APONT_SCREEN
    const val MOV_EQUIP_PROPRIO_LIST_ROUTE = MOV_EQUIP_PROPRIO_LIST_SCREEN
    const val MOV_EQUIP_VISIT_TERC_LIST_ROUTE = MOV_EQUIP_VISIT_TERC_LIST_SCREEN
    const val MOV_EQUIP_RESIDENCIA_LIST_ROUTE = MOV_EQUIP_RESIDENCIA_LIST_SCREEN
    const val MATRIC_COLAB_ROUTE =
        "$MATRIC_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}"
    const val NOME_COLAB_ROUTE =
        "$NOME_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}/{$MATRIC_COLAB_ARGS}"
    const val PASSAG_COLAB_LIST_ROUTE =
        "$PASSAG_COLAB_LIST_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}"
    const val DETALHE_MOV_PROPRIO_ROUTE = DETALHE_PROPRIO_SCREEN
    const val NRO_EQUIP_PROPRIO_ROUTE =
        "$NRO_EQUIP_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_EQUIP_ARGS}/{$ID_ARGS}"
    const val EQUIP_SEG_LIST_ROUTE =
        "$EQUIP_SEG_LIST_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_EQUIP_ARGS}/{$ID_ARGS}"
    const val DESTINO_PROPRIO_ROUTE = "$DESTINO_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val NOTA_FISCAL_PROPRIO_ROUTE = "$NOTA_FISCAL_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val OBSERV_PROPRIO_ROUTE = "$OBSERV_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
}

class NavigationActions(private val navController: NavHostController) {

    fun navigationToSplash() {
        navController.navigate(SPLASH_SCREEN)
    }

    fun navigationToSenha() {
        navController.navigate(SENHA_SCREEN)
    }

    fun navigationToMenuInicial() {
        navController.navigate(MENU_INICIAL_SCREEN)
    }

    fun navigationToConfig() {
        navController.navigate(CONFIG_SCREEN)
    }

    fun navigationToMatricVigia() {
        navController.navigate(MATRIC_VIGIA_SCREEN)
    }

    fun navigationToNomeVigia() {
        navController.navigate(NOME_VIGIA_SCREEN)
    }

    fun navigationToLocal() {
        navController.navigate(LOCAL_SCREEN)
    }

    fun navigationToMenuApont() {
        navController.navigate(MENU_APONT_SCREEN)
    }

    fun navigationToMovEquipProprioList() {
        navController.navigate(MOV_EQUIP_PROPRIO_LIST_SCREEN)
    }

    fun navigationToMovEquipVisitTercList() {
        navController.navigate(MOV_EQUIP_VISIT_TERC_LIST_SCREEN)
    }

    fun navigationToMovEquipResidenciaList() {
        navController.navigate(MOV_EQUIP_RESIDENCIA_LIST_SCREEN)
    }

    fun navigationToMatricColab(
        flowApp: Int,
        typeOcupante: Int,
        id: Int
    ) {
        navController.navigate("${MATRIC_COLAB_SCREEN}/${flowApp}/${typeOcupante}/${id}")
    }

    fun navigationToNomeColab(
        flowApp: Int,
        typeOcupante: Int,
        id: Int,
        matricColab: String
    ) {
        navController.navigate("${NOME_COLAB_SCREEN}/${flowApp}/${typeOcupante}/${id}/${matricColab}")
    }

    fun navigationToPassagColabList(
        flowApp: Int,
        typeOcupante: Int,
        id: Int,
    ) {
        navController.navigate("${PASSAG_COLAB_LIST_SCREEN}/${flowApp}/${typeOcupante}/${id}")
    }

    fun navigationToNroEquip(
        flowApp: Int,
        typeEquip: Int,
        id: Int,
    ) {
        navController.navigate("$NRO_EQUIP_PROPRIO_SCREEN/${flowApp}/${typeEquip}/${id}")
    }

    fun navigationToEquipSegList(
        flowApp: Int,
        typeEquip: Int,
        id: Int,
    ) {
        navController.navigate("${EQUIP_SEG_LIST_SCREEN}/${flowApp}/${typeEquip}/${id}")
    }

    fun navigationToDetalheMovProprio() {
        navController.navigate(DETALHE_PROPRIO_SCREEN)
    }

    fun navigationToDestino(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${DESTINO_PROPRIO_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToNotaFiscal(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${NOTA_FISCAL_PROPRIO_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToObserv(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${OBSERV_PROPRIO_SCREEN}/${flowApp}/${id}")
    }
}