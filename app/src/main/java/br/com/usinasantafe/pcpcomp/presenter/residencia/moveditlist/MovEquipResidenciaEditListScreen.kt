package br.com.usinasantafe.pcpcomp.presenter.residencia.moveditlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
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
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun MovEquipResidenciaEditListScreen(
    viewModel: MovEquipResidenciaEditListViewModel,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: (Int) -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MovEquipResidenciaEditListContent(
                movEquipResidenciaEditModelList = uiState.movEquipResidenciaEditModelList,
                closeAllMov = viewModel::closeAllMov,
                onNavMovEquipList = onNavMovEquipList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverMovEquipEditList()
        }
    }
}

@Composable
fun MovEquipResidenciaEditListContent(
    movEquipResidenciaEditModelList: List<MovEquipResidenciaEditModel>,
    closeAllMov: () -> Unit,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_edit_mov))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(movEquipResidenciaEditModelList) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "TIPO:  ${mov.tipo}\n" +
                            "VEÍCULO: ${mov.veiculo}\n" +
                            "PLACA: ${mov.placa}\n" +
                            "MOTORISTA: ${mov.motorista}\n",
                    setActionItem = {
                        onNavDetalhe(mov.id)
                    },
                    id = mov.id
                )
            }
        }
        Button(
            onClick = closeAllMov,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_close_mov))
        }
        Button(
            onClick = onNavMovEquipList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipResidenciaEditListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipResidenciaEditListContent(
                movEquipResidenciaEditModelList = emptyList(),
                closeAllMov = {},
                onNavMovEquipList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}