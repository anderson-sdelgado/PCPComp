package br.com.usinasantafe.pcpcomp.presenter.splash

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun SplashScreen(
    onNavMenuInicial: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                onNavMenuInicial = onNavMenuInicial
            )
        }
    }
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    onNavMenuInicial: () -> Unit,
) {
    onNavMenuInicial()
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                onNavMenuInicial = {}
            )
        }
    }
}