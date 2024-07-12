package br.com.usinasantafe.pcpcomp.presenter

import androidx.navigation.NavHostController

object Screens {
    const val MENU_INICIAL = "menuinicial"
    const val SENHA = "senha"
    const val CONFIG = "config"
}

class NavigationActions(private val navController: NavHostController) {

    fun navigationToSenha(){
        navController.navigate(Screens.SENHA)
    }

    fun navigationToMenuInicial(){
        navController.navigate(Screens.MENU_INICIAL)
    }

    fun navigationToConfig(){
        navController.navigate(Screens.CONFIG)
    }

}