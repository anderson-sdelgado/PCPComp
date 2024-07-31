package br.com.usinasantafe.pcpcomp.presenter

import androidx.navigation.NavHostController

object Screens {
    const val MENU_INICIAL = "menuinicial"
    const val SENHA = "senha"
    const val CONFIG = "config"
    const val MATRIC_VIGIA = "matricvigia"
    const val SPLASH = "splash"
    const val NOME_VIGIA = "nomevigia"
    const val LOCAL = "local"
}

class NavigationActions(private val navController: NavHostController) {

    fun navigationToSplash() {
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

}