package br.com.usinasantafe.pcpcomp.presenter.chaveequip.controlelist

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
import br.com.usinasantafe.pcpcomp.presenter.chaveequip.model.ControleChaveEquipModel
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

@Composable
fun ControleChaveEquipListScreen(
    viewModel: ControleChaveEquipListViewModel,
    onNavControleChaveEquipEditList: () -> Unit,
    onNavMatricColab: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavEquip: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ControleChaveEquipListContent(
                controleChaveEquipModelList = uiState.controleChaveEquipModelList,
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                startMov = viewModel::startMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavControleChaveEquipEditList = onNavControleChaveEquipEditList,
                onNavMatricColab = onNavMatricColab,
                onNavMenuApont = onNavMenuApont,
                onNavEquip = onNavEquip,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ControleChaveEquipListContent(
    controleChaveEquipModelList: List<ControleChaveEquipModel>,
    descrVigia: String,
    descrLocal: String,
    startMov: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavControleChaveEquipEditList: () -> Unit,
    onNavMatricColab: (Int) -> Unit,
    onNavMenuApont: () -> Unit,
    onNavEquip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        TitleDesign(
            text = stringResource(id = R.string.text_title_controle_chave)
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(controleChaveEquipModelList) { cont ->
                ItemListDesign(
                    text = "DATA/HORA: ${cont.dthr}\n" +
                            "EQUIPAMENTO: ${cont.equip}\n" +
                            "COLABORADOR: ${cont.colab}\n",
                    setActionItem = {
                        onNavMatricColab(cont.id)
                    },
                    id = cont.id,
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
            TextButtonDesign(
                text = stringResource(id = R.string.text_receipt_key)
            )
        }
        Button(
            onClick = onNavControleChaveEquipEditList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_edit_mov)
            )
        }
        Button(
            onClick = onNavMenuApont,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
            onNavEquip()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ControleChaveEquipListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEquipListContent(
                controleChaveEquipModelList = listOf(
                    ControleChaveEquipModel(
                        id = 1,
                        dthr = "08/08/2024 12:00",
                        equip = "300 - TRATOR",
                        colab = "19759 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                descrVigia = "19759 - ANDERSON DA SILVA DELGADO",
                descrLocal = "1 - USINA",
                startMov = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavControleChaveEquipEditList = {},
                onNavMatricColab = {},
                onNavMenuApont = {},
                onNavEquip = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}