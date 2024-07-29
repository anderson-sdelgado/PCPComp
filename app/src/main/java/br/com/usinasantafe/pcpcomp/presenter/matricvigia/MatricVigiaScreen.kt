package br.com.usinasantafe.pcpcomp.presenter.matricvigia

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
import br.com.usinasantafe.pcpcomp.presenter.menuinicial.MenuInicialContent
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun MatricVigiaScreen() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MatricVigiaContent(
    modifier: Modifier = Modifier
) {
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
fun MatricVigiaPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}