package br.com.usinasantafe.pcpcomp.presenter.proprio.passaglist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

@Composable
fun PassagColabListScreen(
    viewModel: PassagColabListViewModel,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PassagColabListContent(
                passagList = uiState.passagList,
                setPassagDelete = viewModel::setPassagDelete,
                modifier = Modifier.padding(innerPadding)
            )
            if(TypeOcupante.MOTORISTA == TypeOcupante.entries[uiState.typeOcupante]){
                viewModel.cleanPassag()
            }
        }
    }
}

@Composable
fun PassagColabListContent(
    passagList: List<Colab>,
    setPassagDelete: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "PASSAGEIRO(S)")
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(passagList) { colab ->
                ItemListDesign(
                    text = "${colab.matricColab} - ${colab.nomeColab}",
                    setActionItem = { setPassagDelete(colab.matricColab) }
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_insert))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { TODO() },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = { TODO() },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}
    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                passagList = emptyList(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreviewList() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                passagList = listOf(
                    Colab(
                        matricColab = 19759,
                        nomeColab = "ANDERSON DA SILVA DELGADO"
                    ),
                    Colab(
                        matricColab = 19035,
                        nomeColab = "JOSE DONIZETE"
                    )
                ),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}