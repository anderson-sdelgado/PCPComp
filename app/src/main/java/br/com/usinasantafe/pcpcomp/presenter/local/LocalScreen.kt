package br.com.usinasantafe.pcpcomp.presenter.local

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme

@Composable
fun LocalScreen() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LocalContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun LocalContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LocalContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}