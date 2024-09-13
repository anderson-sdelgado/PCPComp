package br.com.usinasantafe.pcpcomp.presenter.visitterc.passaglist

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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp

@Composable
fun PassagVisitTercListScreen(
    viewModel: PassagVisitTercListViewModel,
    onNavCpfMotorista: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
    onNavCpfPassag: () -> Unit,
    onNavDestino: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PassagVisitTercListContent(
                flowApp = uiState.flowApp,
                passagList = uiState.passagList,
                setDelete = viewModel::setDelete,
                flagDialogCheck = uiState.flagDialogCheck,
                setCloseDialogCheck = viewModel::setCloseDialogCheck,
                setDeletePassag = viewModel::deletePassag,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavCpfMotorista = onNavCpfMotorista,
                onNavDetalheVisitTerc = onNavDetalheVisitTerc,
                onNavCpfPassag = onNavCpfPassag,
                onNavDestino = onNavDestino,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.cleanPassag()
            viewModel.recoverPassag()
        }
    }
}

@Composable
fun PassagVisitTercListContent(
    flowApp: FlowApp,
    passagList: List<PassagVisitTercModel>,
    setDelete: (Int) -> Unit,
    flagDialogCheck: Boolean,
    setCloseDialogCheck: () -> Unit,
    setDeletePassag: () -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavCpfMotorista: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
    onNavCpfPassag: () -> Unit,
    onNavDestino: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_passag))
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(passagList) { passag ->
                ItemListDesign(
                    text = "${passag.cpf} - ${passag.nome}",
                    setActionItem = { setDelete(passag.id) }
                )
            }
        }
        Button(
            onClick = { onNavCpfPassag() },
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
                        FlowApp.ADD -> onNavCpfMotorista()
                        FlowApp.CHANGE -> onNavDetalheVisitTerc()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = { onNavDestino() },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if(flagDialog) {
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
fun PassagVisitTercListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PassagVisitTercListContent(
                flowApp = FlowApp.ADD,
                passagList = emptyList(),
                setDelete = {},
                flagDialogCheck = false,
                setCloseDialogCheck = {},
                setDeletePassag = {},
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavCpfMotorista = {},
                onNavDetalheVisitTerc = {},
                onNavCpfPassag = {},
                onNavDestino = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}