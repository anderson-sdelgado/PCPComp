package br.com.usinasantafe.pcpcomp.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.pcpcomp.presenter.Args.FLOW_APP_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.MATRIC_COLAB_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.POS_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Args.TYPE_OCUPANTE_ARGS
import br.com.usinasantafe.pcpcomp.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.DETALHE_MOV_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.PASSAG_COLAB_LIST_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.LOCAL_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_COLAB_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MATRIC_VIGIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_APONT_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MENU_INICIAL_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_PROPRIO_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_RESIDENCIA_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.MOV_VISIT_TERC_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_COLAB_SCREEN
import br.com.usinasantafe.pcpcomp.presenter.Screens.NOME_VIGIA_SCREEN
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
    const val MOV_PROPRIO_SCREEN = "movproprio"
    const val MATRIC_COLAB_SCREEN = "matriccolab"
    const val NOME_COLAB_SCREEN = "nomecolab"
    const val MOV_VISIT_TERC_SCREEN = "movvisitterc"
    const val MOV_RESIDENCIA_SCREEN = "movresidencia"
    const val DETALHE_MOV_PROPRIO_SCREEN = "detalhemovproprio"
    const val PASSAG_COLAB_LIST_SCREEN = "passagcolablist"
}

object Args {
    const val FLOW_APP_ARGS = "flowApp"
    const val TYPE_OCUPANTE_ARGS = "typeOcupante"
    const val POS_ARGS = "pos"
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
    const val MOV_PROPRIO_ROUTE = MOV_PROPRIO_SCREEN
    const val MATRIC_COLAB_ROUTE = "$MATRIC_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$POS_ARGS}"
    const val NOME_COLAB_ROUTE = "$NOME_COLAB_SCREEN/{$FLOW_APP_ARGS}/{$TYPE_OCUPANTE_ARGS}/{$POS_ARGS}/{$MATRIC_COLAB_ARGS}"
    const val PASSAG_COLAB_LIST_ROUTE = PASSAG_COLAB_LIST_SCREEN
    const val DETALHE_MOV_PROPRIO_ROUTE = DETALHE_MOV_PROPRIO_SCREEN
}

class NavigationActions(private val navController: NavHostController) {

    fun navigationToSplash(){
        navController.navigate(SPLASH_SCREEN)
    }

    fun navigationToSenha(){
        navController.navigate(SENHA_SCREEN)
    }

    fun navigationToMenuInicial(){
        navController.navigate(MENU_INICIAL_SCREEN)
    }

    fun navigationToConfig(){
        navController.navigate(CONFIG_SCREEN)
    }

    fun navigationToMatricVigia(){
        navController.navigate(MATRIC_VIGIA_SCREEN)
    }

    fun navigationToNomeVigia(){
        navController.navigate(NOME_VIGIA_SCREEN)
    }

    fun navigationToLocal(){
        navController.navigate(LOCAL_SCREEN)
    }

    fun navigationToMenuApont(){
        navController.navigate(MENU_APONT_SCREEN)
    }

    fun navigationToMovProprio(){
        navController.navigate(MOV_PROPRIO_SCREEN)
    }

    fun navigationToMovVisitTerc(){
        navController.navigate(MOV_VISIT_TERC_SCREEN)
    }

    fun navigationToMovResidencia(){
        navController.navigate(MOV_RESIDENCIA_SCREEN)
    }

    fun navigationToMatricColab(
        flowApp: Int,
        typeOcupante: Int,
        pos: Int = 0
    ){
        navController.navigate("${MATRIC_COLAB_SCREEN}/${flowApp}/${typeOcupante}/${pos}")
    }

    fun navigationToNomeColab(
        flowApp: Int,
        typeOcupante: Int,
        pos: Int = 0,
        matricColab: String
    ){
        navController.navigate("${NOME_COLAB_SCREEN}/${flowApp}/${typeOcupante}/${pos}/${matricColab}")
    }

    fun navigationToPassagColabList(
        flowApp: Int,
        typeOcupante: Int,
        pos: Int = 0,
    ){
        navController.navigate("${PASSAG_COLAB_LIST_SCREEN}/${flowApp}/${typeOcupante}/${pos}")
    }

    fun navigationToDetalheMovProprio(){
        navController.navigate(DETALHE_MOV_PROPRIO_SCREEN)
    }

}