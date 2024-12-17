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
import br.com.usinasantafe.pcpcomp.presenter.residencia.model.MovEquipResidenciaModel
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

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
                movEquipResidenciaModelList = uiState.movEquipResidenciaModelList,
                closeAllMov = viewModel::closeAllMov,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                setCloseDialog = viewModel::setCloseDialog,
                flagCloseAllMov = uiState.flagCloseAllMov,
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
    movEquipResidenciaModelList: List<MovEquipResidenciaModel>,
    closeAllMov: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    setCloseDialog: () -> Unit,
    flagCloseAllMov: Boolean,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_edit_mov))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(movEquipResidenciaModelList) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "TIPO:  ${mov.tipoMov}\n" +
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

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavMovEquipList() }
            )
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { setDialogCheck(false)  },
                setActionButtonOK = { closeAllMov() }
            )
        }

        if(flagCloseAllMov){
            onNavMovEquipList()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipResidenciaEditListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipResidenciaEditListContent(
                movEquipResidenciaModelList = emptyList(),
                closeAllMov = {},
                flagDialog = false,
                failure = "",
                flagDialogCheck = false,
                setDialogCheck = {},
                setCloseDialog = {},
                flagCloseAllMov = false,
                onNavMovEquipList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovEquipResidenciaEditDataListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipResidenciaEditListContent(
                movEquipResidenciaModelList = listOf(
                    MovEquipResidenciaModel(
                        id = 1,
                        tipoMov = "ENTRADA",
                        dthr = "20/10/2024",
                        veiculo = "Gol",
                        placa = "abc1234",
                        motorista = "Anderson"
                    )
                ),
                closeAllMov = {},
                flagDialog = false,
                failure = "",
                flagDialogCheck = false,
                setDialogCheck = {},
                setCloseDialog = {},
                flagCloseAllMov = false,
                onNavMovEquipList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}