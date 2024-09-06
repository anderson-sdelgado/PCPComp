package br.com.usinasantafe.pcpcomp.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.pcpcomp.presenter.Args.CPF_VISIT_TERC_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.ID_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_EQUIP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_MOV_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.CPF_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DESTINO_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DESTINO_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DETALHE_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DETALHE_RESIDENCIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DETALHE_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NRO_EQUIP_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.EQUIP_SEG_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.PASSAG_COLAB_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.LOCAL_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_COLAB_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_VIGIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_APONT_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_INICIAL_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOTORISTA_RESIDENCIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_PROPRIO_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_RESIDENCIA_EDIT_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_RESIDENCIA_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_VISIT_TERC_EDIT_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_EQUIP_VISIT_TERC_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_COLAB_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_VIGIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOTA_FISCAL_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.OBSERV_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.OBSERV_RESIDENCIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.OBSERV_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.PASSAG_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.PLACA_RESIDENCIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.PLACA_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.SENHA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.SPLASH_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.TIPO_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.VEICULO_RESIDENCIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.VEICULO_VISIT_TERC_SCREEN

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
    const val DETALHE_PROPRIO_SCREEN = "detalheproprio"
    const val PASSAG_COLAB_LIST_SCREEN = "passagcolablist"
    const val NRO_EQUIP_PROPRIO_SCREEN = "equipproprio"
    const val EQUIP_SEG_LIST_SCREEN = "equipseglist"
    const val DESTINO_PROPRIO_SCREEN = "destinoproprio"
    const val NOTA_FISCAL_PROPRIO_SCREEN = "notafiscalproprio"
    const val OBSERV_PROPRIO_SCREEN = "observproprio"
    const val MOV_EQUIP_VISIT_TERC_LIST_SCREEN = "movequipvisitterclist"
    const val CPF_VISIT_TERC_SCREEN = "cpfvisitterc"
    const val DESTINO_VISIT_TERC_SCREEN = "destinovisitterc"
    const val DETALHE_VISIT_TERC_SCREEN = "detalhevisitterc"
    const val MOV_EQUIP_VISIT_TERC_EDIT_SCREEN = "movequipvisitterceditlist"
    const val NOME_VISIT_TERC_SCREEN = "nomevisitterc"
    const val OBSERV_VISIT_TERC_SCREEN = "observvisitterc"
    const val PASSAG_VISIT_TERC_SCREEN = "passagvisitterc"
    const val PLACA_VISIT_TERC_SCREEN = "placavisitterc"
    const val TIPO_VISIT_TERC_SCREEN = "tipovisitterc"
    const val VEICULO_VISIT_TERC_SCREEN = "veiculovisitterc"
    const val MOV_EQUIP_RESIDENCIA_LIST_SCREEN = "movequipresidencialist"
    const val MOTORISTA_RESIDENCIA_SCREEN = "motoristaresidencia"
    const val OBSERV_RESIDENCIA_SCREEN = "observresidencia"
    const val PLACA_RESIDENCIA_SCREEN = "placaresidencia"
    const val VEICULO_RESIDENCIA_SCREEN = "veiculoresidencia"
    const val MOV_EQUIP_RESIDENCIA_EDIT_LIST_SCREEN = "movequipresidencialeditlist"
    const val DETALHE_RESIDENCIA_SCREEN = "detalheresidencia"
}

object Args {
    const val FLOW_APP_ARGS = "flowApp"
    const val TYPE_OCUPANTE_ARGS = "typeOcupante"
    const val TYPE_EQUIP_ARGS = "typeEquip"
    const val TYPE_MOV_ARGS = "typeMov"
    const val ID_ARGS = "id"
    const val MATRIC_COLAB_ARGS = "matricColab"
    const val CPF_VISIT_TERC_ARGS = "cpfVisitTerc"
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
    const val MATRIC_COLAB_ROUTE =
        "$MATRIC_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}"
    const val NOME_COLAB_ROUTE =
        "$NOME_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}/{$MATRIC_COLAB_ARGS}"
    const val PASSAG_COLAB_LIST_ROUTE =
        "$PASSAG_COLAB_LIST_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}"
    const val DETALHE_MOV_PROPRIO_ROUTE = "$DETALHE_PROPRIO_SCREEN/{$ID_ARGS}"
    const val NRO_EQUIP_PROPRIO_ROUTE =
        "$NRO_EQUIP_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_EQUIP_ARGS}/{$ID_ARGS}"
    const val EQUIP_SEG_LIST_ROUTE =
        "$EQUIP_SEG_LIST_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_EQUIP_ARGS}/{$ID_ARGS}"
    const val DESTINO_PROPRIO_ROUTE = "$DESTINO_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val NOTA_FISCAL_PROPRIO_ROUTE = "$NOTA_FISCAL_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val OBSERV_PROPRIO_ROUTE = "$OBSERV_PROPRIO_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val MOV_EQUIP_VISIT_TERC_LIST_ROUTE = MOV_EQUIP_VISIT_TERC_LIST_SCREEN
    const val VEICULO_VISIT_TERC_ROUTE = "$VEICULO_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val PLACA_VISIT_TERC_ROUTE = "$PLACA_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val TIPO_VISIT_TERC_ROUTE = TIPO_VISIT_TERC_SCREEN
    const val CPF_VISIT_TERC_ROUTE =
        "$CPF_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}"
    const val NOME_VISIT_TERC_ROUTE =
        "$NOME_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}/{$CPF_VISIT_TERC_ARGS}"
    const val PASSAG_VISIT_TERC_ROUTE =
        "$PASSAG_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$ID_ARGS}"
    const val DESTINO_VISIT_TERC_ROUTE = "$DESTINO_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val OBSERV_VISIT_TERC_ROUTE = "$OBSERV_VISIT_TERC_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_MOV_ARGS}/{$ID_ARGS}"
    const val MOV_EQUIP_VISIT_TERC_EDIT_ROUTE = MOV_EQUIP_VISIT_TERC_EDIT_SCREEN
    const val DETALHE_VISIT_TERC_ROUTE = "$DETALHE_VISIT_TERC_SCREEN/{$ID_ARGS}"
    const val MOV_EQUIP_RESIDENCIA_LIST_ROUTE = MOV_EQUIP_RESIDENCIA_LIST_SCREEN
    const val VEICULO_RESIDENCIA_ROUTE = "$VEICULO_RESIDENCIA_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val PLACA_RESIDENCIA_ROUTE = "$PLACA_RESIDENCIA_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val MOTORISTA_RESIDENCIA_ROUTE = "$MOTORISTA_RESIDENCIA_SCREEN/{$FLOW_APP_ARGS}/{$ID_ARGS}"
    const val OBSERV_RESIDENCIA_ROUTE = "$OBSERV_RESIDENCIA_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_MOV_ARGS}/{$ID_ARGS}"
    const val MOV_EQUIP_RESIDENCIA_EDIT_LIST_ROUTE = MOV_EQUIP_RESIDENCIA_EDIT_LIST_SCREEN
    const val DETALHE_RESIDENCIA_ROUTE = "$DETALHE_RESIDENCIA_SCREEN/{$ID_ARGS}"
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

    fun navigationToDetalheProprio(
        id: Int
    ) {
        navController.navigate("${DETALHE_PROPRIO_SCREEN}/${id}")
    }

    fun navigationToDestinoProprio(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${DESTINO_PROPRIO_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToNotaFiscalProprio(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${NOTA_FISCAL_PROPRIO_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToObservProprio(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${OBSERV_PROPRIO_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToMovEquipVisitTercList() {
        navController.navigate(MOV_EQUIP_VISIT_TERC_LIST_SCREEN)
    }

    fun navigationToVeiculoVisitTerc(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${VEICULO_VISIT_TERC_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToPlacaVisitTerc(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${VEICULO_VISIT_TERC_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToTipoVisitTerc() {
        navController.navigate(TIPO_VISIT_TERC_SCREEN)
    }

    fun navigationToCpfVisitTerc(
        flowApp: Int,
        typeOcupante: Int,
        id: Int,
    ) {
        navController.navigate("${CPF_VISIT_TERC_SCREEN}/${flowApp}/${typeOcupante}/${id}")
    }

    fun navigationToNomeVisitTerc(
        flowApp: Int,
        typeOcupante: Int,
        id: Int,
        cpfVisitTerc: String
    ) {
        navController.navigate("${CPF_VISIT_TERC_SCREEN}/${flowApp}/${typeOcupante}/${id}/${cpfVisitTerc}")
    }

    fun navigationToPassagVisitTerc(
        flowApp: Int,
        typeOcupante: Int,
        id: Int,
    ) {
        navController.navigate("${PASSAG_VISIT_TERC_SCREEN}/${flowApp}/${typeOcupante}/${id}")
    }

    fun navigationToDestinoVisitTerc(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${DESTINO_VISIT_TERC_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToObservVisitTerc(
        flowApp: Int,
        typeMov: Int,
        id: Int,
    ) {
        navController.navigate("${OBSERV_VISIT_TERC_SCREEN}/${flowApp}/${typeMov}/${id}")
    }

    fun navigationToMovEquipVisitTercEditList() {
        navController.navigate(MOV_EQUIP_VISIT_TERC_EDIT_SCREEN)
    }

    fun navigationToDetalheVisitTerc(
        id: Int,
    ) {
        navController.navigate("${DETALHE_VISIT_TERC_SCREEN}/${id}")
    }

    fun navigationToMovEquipResidenciaList() {
        navController.navigate(MOV_EQUIP_RESIDENCIA_LIST_SCREEN)
    }

    fun navigationToVeiculoResidencia(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${VEICULO_RESIDENCIA_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToPlacaResidencia(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${PLACA_RESIDENCIA_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToMotoristaResidencia(
        flowApp: Int,
        id: Int,
    ) {
        navController.navigate("${MOTORISTA_RESIDENCIA_SCREEN}/${flowApp}/${id}")
    }

    fun navigationToObservResidencia(
        flowApp: Int,
        typeMov: Int,
        id: Int,
    ) {
        navController.navigate("${OBSERV_RESIDENCIA_SCREEN}/${flowApp}/${typeMov}/${id}")
    }

    fun navigationToMovEquipResidenciaEditList() {
        navController.navigate(MOV_EQUIP_RESIDENCIA_EDIT_LIST_SCREEN)
    }

    fun navigationToDetalheResidencia(
        id: Int,
    ) {
        navController.navigate("${DETALHE_RESIDENCIA_SCREEN}/${id}")
    }

}