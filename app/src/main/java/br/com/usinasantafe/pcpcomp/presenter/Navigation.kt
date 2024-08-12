package br.com.usinasantafe.pcpcomp.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

object Screens {
    const val MENU_INICIAL = "menuinicial"
    const val SENHA = "senha"
    const val CONFIG = "config"
    const val MATRIC_VIGIA = "matricvigia"
    const val SPLASH = "splash"
    const val NOME_VIGIA = "nomevigia"
    const val LOCAL = "local"
    const val MENU_APONT = "menuapont"
    const val MOV_VEIC_PROPRIO = "movveicproprio"
    const val MATRIC_COLAB = "matriccolab"
    const val MOV_VEIC_VISIT_TERC = "movveicvisitterc"
    const val MOV_VEIC_RESIDENCIA = "movveicresidencia"
}

class NavigationActions(private val navController: NavHostController) {


    fun navigationToSplash(){
        navController.navigate(Screens.SPLASH)
    }


    fun navigationToSenha(){
        navController.navigate(Screens.SENHA)
    }

    fun navigationToMenuInicial(){
        navController.navigate(Screens.MENU_INICIAL)
    }

    fun navigationToConfig(){
        navController.navigate(Screens.CONFIG)
    }

    fun navigationToMatricVigia(){
        navController.navigate(Screens.MATRIC_VIGIA)
    }

    fun navigationToNomeVigia(){
        navController.navigate(Screens.NOME_VIGIA)
    }

    fun navigationToLocal(){
        navController.navigate(Screens.LOCAL)
    }

    fun navigationToMenuApont(){
        navController.navigate(Screens.MENU_APONT)
    }

    fun navigationToMovVeicProprio(){
        navController.navigate(Screens.MOV_VEIC_PROPRIO)
    }

    fun navigationToMovVeicVisitTerc(){
        navController.navigate(Screens.MOV_VEIC_VISIT_TERC)
    }

    fun navigationToMovVeicResidencia(){
        navController.navigate(Screens.MOV_VEIC_RESIDENCIA)
    }

    fun navigationToMatriColab(flowApp: Int){
        navController.navigate("${Screens.MATRIC_COLAB}/${flowApp}")
    }


}