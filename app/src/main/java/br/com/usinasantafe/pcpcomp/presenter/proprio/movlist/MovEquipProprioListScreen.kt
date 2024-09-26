package br.com.usinasantafe.pcpcomp.presenter.proprio.movlist

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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListWithDetailDesign
import br.com.usinasantafe.pcpcomp.utils.TypeMov

@Composable
fun MovEquipProprioListScreen(
    viewModel: MovEquipProprioListViewModel,
    onNavNroEquip: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavSplashScreen: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MovEquipProprioListContent(
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                movEquipProprioModelList = uiState.movEquipProprioModelList,
                startMov = viewModel::startMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                closeAllMov = viewModel::closeAllMov,
                flagCloseAllMov = uiState.flagCloseAllMov,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavNroEquip = onNavNroEquip,
                onNavDetalhe = onNavDetalhe,
                onNavMenuApont = onNavMenuApont,
                onNavSplashScreen = onNavSplashScreen,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnHeader()
            viewModel.recoverMovEquipOpenList()
        }
    }
}

@Composable
fun MovEquipProprioListContent(
    descrVigia: String,
    descrLocal: String,
    movEquipProprioModelList: List<MovEquipProprioModel>,
    startMov: (TypeMov) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    closeAllMov: () -> Unit,
    flagCloseAllMov: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavNroEquip: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavSplashScreen: () -> Unit,
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
            items(movEquipProprioModelList) { mov ->
                ItemListDesign(
                    text = "${mov.dthr}\n" +
                            "${mov.typeMov}\n" +
                            "${mov.equip}\n" +
                            "${mov.colab}\n",
                    setActionItem = {
                        onNavDetalhe(mov.id)
                    },
                    id = mov.id
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { startMov(TypeMov.INPUT) },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_input))
            }
            Button(
                onClick = { startMov(TypeMov.OUTPUT) },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_output))
            }
        }
        Button(
            onClick = closeAllMov,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_close_mov))
        }
        Button(
            onClick = onNavMenuApont,
            modifier = Modifier.fillMaxWidth(),
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
            onNavNroEquip()
        }

        if(flagCloseAllMov){
            onNavSplashScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipProprioListPreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipProprioListContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                movEquipProprioModelList = listOf(
                    MovEquipProprioModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    ),
                    MovEquipProprioModel(
                        id = 2,
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                startMov = {},
                flagAccess = false,
                flagDialog = false,
                closeAllMov = {},
                flagCloseAllMov = false,
                setCloseDialog = {},
                failure = "",
                onNavNroEquip = {},
                onNavDetalhe = {},
                onNavMenuApont = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovEquipProprioListPreviewShowDialog() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MovEquipProprioListContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                movEquipProprioModelList = listOf(
                    MovEquipProprioModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        typeMov = "Entrada",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    ),
                    MovEquipProprioModel(
                        id = 2,
                        dthr = "08/08/2024 12:00",
                        typeMov = "ENTRADA",
                        equip = "2300",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                startMov = {},
                flagAccess = false,
                flagDialog = true,
                closeAllMov = {},
                flagCloseAllMov = false,
                setCloseDialog = {},
                failure = "Failure Datasource",
                onNavNroEquip = {},
                onNavDetalhe = {},
                onNavMenuApont = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}