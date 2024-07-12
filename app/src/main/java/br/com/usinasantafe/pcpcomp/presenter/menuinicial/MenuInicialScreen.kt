package br.com.usinasantafe.pcpcomp.presenter.menuinicial

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun MenuInicialScreen(onNavSenha: () -> Unit) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                modifier = Modifier.padding(innerPadding),
                onNavSenha
            )
        }
    }
}

@Composable
fun MenuInicialContent(modifier: Modifier = Modifier, onNavSenha: () -> Unit) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "MENU INICIAL")
        LazyColumn {
            item {
                ItemListDesign("APONTAMENTO", onNavigation = {})
            }
            item {
                ItemListDesign("CONFIGURAÇÃO", onNavigation = onNavSenha)
            }
            item {
                ItemListDesign(
                    "SAIR",
                    onNavigation = {
                        activity?.finish()
                    }
                )
            }
        }
        BackHandler {}
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                modifier = Modifier.padding(innerPadding),
                onNavSenha = {}
            )
        }
    }
}
