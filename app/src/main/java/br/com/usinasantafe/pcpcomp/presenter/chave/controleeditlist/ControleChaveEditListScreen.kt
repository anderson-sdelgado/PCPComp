package br.com.usinasantafe.pcpcomp.presenter.chave.controleeditlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

@Composable
fun ControleChaveEditListScreen() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEditListContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ControleChaveEditListContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(id = R.string.text_title_edit_mov)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ControleChaveEditListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEditListContent(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}