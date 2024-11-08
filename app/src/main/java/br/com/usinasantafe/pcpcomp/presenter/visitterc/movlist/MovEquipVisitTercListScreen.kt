package br.com.usinasantafe.pcpcomp.presenter.visitterc.movlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import br.com.usinasantafe.pcpcomp.presenter.visitterc.model.MovEquipVisitTercModel
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

@Composable
fun MovEquipVisitTercListScreen(
    viewModel: MovEquipVisitTercListViewModel,
    onNavVeiculo: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavObserv: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MovEquipVisitTercListContent(
                movEquipVisitTercModelList = uiState.movEquipVisitTercModelList,
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                startMov = viewModel::startMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavVeiculo = onNavVeiculo,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavObserv = onNavObserv,
                onNavMenuApont = onNavMenuApont,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnHeader()
            viewModel.recoverMovEquipList()
        }
    }
}

@Composable
fun MovEquipVisitTercListContent(
    movEquipVisitTercModelList: List<MovEquipVisitTercModel>,
    descrVigia: String,
    descrLocal: String,
    startMov: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
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
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        TitleDesign(text = stringResource(id = R.string.text_title_mov_visit_terc))
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(movEquipVisitTercModelList) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "VEÍCULO: ${mov.veiculo}\n" +
                            "PLACA: ${mov.placa}\n" +
                            "${mov.tipoVisitTerc}\n" +
                            "MOTORISTA: ${mov.motorista}\n",
                    setActionItem = {
                        onNavObserv(mov.id)
                    },
                    id = mov.id,
                    padding = 0
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
fun MovEquipVisitTercListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipVisitTercListContent(
                descrVigia = "19759 - ANDERSON DA SILVA DELGADO",
                descrLocal = "1 - USINA",
                movEquipVisitTercModelList = listOf(
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        motorista = "326.949.728-88 - ANDERSON DA SILVA DELGADO",
                        veiculo = "GOL",
                        placa = "ABC1234",
                        tipoVisitTerc = "TERCEIRO"
                    ),
                    MovEquipVisitTercModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        motorista = "123.456.789-00 - TESTE",
                        veiculo = "GOL",
                        placa = "ABC1234",
                        tipoVisitTerc = "TERCEIRO"
                    )
                ),
                onNavObserv = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavVeiculo = {},
                onNavMovEquipEditList = {},
                startMov = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}