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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp

@Composable
fun PassagColabListScreen(
    viewModel: PassagColabListViewModel,
    onNavMatricMotorista: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMatricPassag: () -> Unit,
    onNavDestino: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PassagColabListContent(
                flowApp = uiState.flowApp,
                passagList = uiState.passagList,
                setDelete = viewModel::setDelete,
                flagDialogCheck = uiState.flagDialogCheck,
                setCloseDialogCheck = viewModel::setCloseDialogCheck,
                setDeletePassag = viewModel::deletePassag,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMatricColab = onNavMatricMotorista,
                onNavDetalhe = onNavDetalhe,
                onNavMatricPassag = onNavMatricPassag,
                onNavDestino = onNavDestino,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.cleanPassag()
            viewModel.recoverPassag()
        }
    }
}

@Composable
fun PassagColabListContent(
    flowApp: FlowApp,
    passagList: List<Colab>,
    setDelete: (Int) -> Unit,
    flagDialogCheck: Boolean,
    setCloseDialogCheck: () -> Unit,
    setDeletePassag: () -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMatricColab: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMatricPassag: () -> Unit,
    onNavDestino: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_passag))
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(passagList) { colab ->
                ItemListDesign(
                    text = "${colab.matricColab} - ${colab.nomeColab}",
                    setActionItem = { setDelete(colab.matricColab) }
                )
            }
        }
        Button(
            onClick = { onNavMatricPassag() },
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
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavMatricColab()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavDestino()
                        FlowApp.CHANGE -> onNavDetalhe()
                    }
                },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagDialogCheck) {
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_delete_passag),
                setCloseDialog = setCloseDialogCheck,
                setActionButtonOK = setDeletePassag
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagColabListContent(
                passagList = emptyList(),
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeletePassag = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
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
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeletePassag = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreviewFailure() {
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
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeletePassag = {},
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PassagColabListPagePreviewMsgDelete() {
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
                flowApp = FlowApp.ADD,
                setDelete = {},
                flagDialogCheck = true,
                setCloseDialogCheck = {},
                setDeletePassag = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavMatricColab = {},
                onNavDetalhe = {},
                onNavMatricPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}