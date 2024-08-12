package br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun MatricColabScreen(
    flowApp: Int
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                flowApp = flowApp,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MatricColabContent(
    flowApp: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "MATRIC. COLABORADOR: $flowApp")
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                flowApp = 1,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}