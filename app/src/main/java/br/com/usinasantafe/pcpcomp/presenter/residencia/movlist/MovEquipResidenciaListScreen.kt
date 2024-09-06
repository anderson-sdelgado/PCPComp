package br.com.usinasantafe.pcpcomp.presenter.residencia.movlist

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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun MovEquipResidenciaListScreen(
    viewModel: MovEquipResidenciaListViewModel,
    onNavVeiculo: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavObserv: (Int) -> Unit,
    onNavMenuApont: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MovEquipResidenciaListContent(
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                movEquipResidenciaModelList = uiState.movEquipResidenciaModelList,
                startMov = viewModel::startMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                setCloseDialog = viewModel::setCloseDialog,
                onNavVeiculo = onNavVeiculo,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavObserv = onNavObserv,
                onNavMenuApont = onNavMenuApont,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MovEquipResidenciaListContent(
    descrVigia: String,
    descrLocal: String,
    movEquipResidenciaModelList: List<MovEquipResidenciaModel> = emptyList(),
    startMov: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    setCloseDialog: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavObserv: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        TitleListDesign(text = stringResource(id = R.string.text_title_mov_residencia))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(movEquipResidenciaModelList) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "VEÍCULO: ${mov.veiculo}\n" +
                            "PLACA: ${mov.placa}\n" +
                            "MOTORISTA: ${mov.motorista}\n",
                    setActionItem = {
                        onNavObserv(mov.id)
                    },
                    id = mov.id
                )
            }
        }
        Button(
            onClick = startMov,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_input))
        }
        Button(
            onClick = onNavMovEquipEditList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_edit_mov))
        }
        Button(
            onClick = onNavMenuApont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
            onNavVeiculo()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipResidenciaListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipResidenciaListContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                movEquipResidenciaModelList = emptyList(),
                startMov = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                setCloseDialog = {},
                onNavVeiculo = {},
                onNavMovEquipEditList = {},
                onNavObserv = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}