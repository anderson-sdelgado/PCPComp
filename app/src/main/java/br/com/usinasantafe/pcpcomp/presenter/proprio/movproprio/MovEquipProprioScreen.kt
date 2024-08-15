package br.com.usinasantafe.pcpcomp.presenter.proprio.movproprio

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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListWithDetailDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.TypeMov

@Composable
fun MovEquipProprioScreen(
    viewModel: MovEquipProprioViewModel,
    onNavMatricColab: (Int, Int, Int) -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MovEquipProprioContent(
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                movEquipProprioModels = uiState.movEquipProprioModels,
                startMovProprio = viewModel::startMovProprio,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMatricColab = onNavMatricColab,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnHeader()
            viewModel.recoverMovEquipOpenList()
        }
    }
}

@Composable
fun MovEquipProprioContent(
    movEquipProprioModels: List<MovEquipProprioModel>,
    descrVigia: String,
    descrLocal: String,
    startMovProprio: (TypeMov) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMatricColab: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        TitleListWithDetailDesign(text = stringResource(id = R.string.text_title_mov_proprio))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(movEquipProprioModels) { mov ->
                ItemListDesign(
                    text = "DATA/HORA: ${mov.dthr}\n" +
                            "${mov.typeMov}\n" +
                            "VEÍCULO: ${mov.equip}\n" +
                            "MOTORISTA: ${mov.colab}\n",
                    setActionItem = { }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { startMovProprio(TypeMov.INPUT) },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_input))
            }
            Button(
                onClick = { startMovProprio(TypeMov.OUTPUT) },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_output))
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_close_mov))
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
            onNavMatricColab(
                FlowApp.ADD.ordinal,
                TypeEquip.VEICULO.ordinal,
                0
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipProprioPreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipProprioContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                movEquipProprioModels = listOf(
                    MovEquipProprioModel(
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    ),
                    MovEquipProprioModel(
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                startMovProprio = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMatricColab = {_, _, _ ->},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipProprioPreviewShowDialog() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipProprioContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                movEquipProprioModels = listOf(
                    MovEquipProprioModel(
                        dthr = "08/08/2024 12:00",
                        typeMov = "Entrada",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    ),
                    MovEquipProprioModel(
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                startMovProprio = {},
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Datasource",
                onNavMatricColab = {_, _, _ ->},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}