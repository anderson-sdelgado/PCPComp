package br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal

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
fun NotaFiscalProprioScreen() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NotaFiscalProprioContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "")
    }
}

@Preview(showBackground = true)
@Composable
fun NotaFiscalProprioPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}