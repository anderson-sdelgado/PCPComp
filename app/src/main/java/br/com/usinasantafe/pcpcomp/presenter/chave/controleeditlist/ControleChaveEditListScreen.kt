package br.com.usinasantafe.pcpcomp.presenter.chave.controleeditlist

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
import br.com.usinasantafe.pcpcomp.presenter.chave.model.ControleChaveModel
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

@Composable
fun ControleChaveEditListScreen(
    viewModel: ControleChaveEditListViewModel,
    onNavControleList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ControleChaveEditListContent(
                controleChaveModelList = uiState.controleChaveModelList,
                closeAllMov = viewModel::closeAllMov,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                setCloseDialog = viewModel::setCloseDialog,
                flagCloseAllMov = uiState.flagCloseAllMov,
                onNavControleList = onNavControleList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverMovOpenList()
        }
    }
}

@Composable
fun ControleChaveEditListContent(
    controleChaveModelList: List<ControleChaveModel>,
    closeAllMov: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    setCloseDialog: () -> Unit,
    flagCloseAllMov: Boolean,
    onNavControleList: () -> Unit,
    onNavDetalhe: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(id = R.string.text_title_edit_mov)
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(controleChaveModelList) { cont ->
                ItemListDesign(
                    text = "DATA/HORA: ${cont.dthr}\n" +
                            "TIPO: ${cont.tipoMov}\n" +
                            "CHAVE: ${cont.chave}\n" +
                            "COLABORADOR: ${cont.colab}\n",
                    setActionItem = {
                        onNavDetalhe(cont.id)
                    },
                    id = cont.id,
                    padding = 0
                )
            }
        }
        Button(
            onClick = { setDialogCheck(true) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_close_mov
                )
            )
        }
        Button(
            onClick = onNavControleList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(
                    id = R.string.text_pattern_return
                )
            )
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavControleList() }
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
            onNavControleList()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ControleChaveEditListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEditListContent(
                controleChaveModelList = emptyList(),
                closeAllMov = {},
                flagDialog = false,
                failure = "",
                flagDialogCheck = false,
                setDialogCheck = {},
                setCloseDialog = {},
                flagCloseAllMov = false,
                onNavControleList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ControleChaveEditListPagePreviewWithData() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ControleChaveEditListContent(
                controleChaveModelList = listOf(
                    ControleChaveModel(
                        id = 1,
                        tipoMov = "RETIRADA",
                        dthr = "20/10/2024",
                        chave = "01 - SALA TI - TI",
                        colab = "19795 - ANDERSON DA SILVA DELGADO"
                    ),
                    ControleChaveModel(
                        id = 2,
                        tipoMov = "DEVOLUÇÃO",
                        dthr = "20/10/2024",
                        chave = "01 - SALA TI - TI",
                        colab = "19795 - ANDERSON DA SILVA DELGADO"
                    )
                ),
                closeAllMov = {},
                flagDialog = false,
                failure = "",
                flagDialogCheck = false,
                setDialogCheck = {},
                setCloseDialog = {},
                flagCloseAllMov = false,
                onNavControleList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}