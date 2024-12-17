package br.com.usinasantafe.pcpcomp.presenter.chaveequip.nomecolab

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp

@Composable
fun NomeColabChaveEquipScreen(
    viewModel: NomeColabChaveEquipViewModel,
    onNavMatricColab: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalhe: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NomeColabChaveEquipContent(
                flowApp = uiState.flowApp,
                nomeColab = uiState.nomeColab,
                setMatric = viewModel::setMatricColab,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMatricColab = onNavMatricColab,
                onNavObserv = onNavObserv,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnNomeColab()
        }
    }
}

@Composable
fun NomeColabChaveEquipContent(
    flowApp: FlowApp,
    nomeColab: String,
    setMatric: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMatricColab: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(id = R.string.text_title_nome_colab)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = nomeColab,
                fontSize = 28.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = onNavMatricColab,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_cancel)
                )
            }
            Button(
                onClick = setMatric,
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
                setActionButtonOK = { onNavMatricColab() }
            )
        }

        if(flagAccess) {
            when(flowApp){
                FlowApp.ADD -> onNavObserv()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NomeColabChaveEquipPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NomeColabChaveEquipContent(
                flowApp = FlowApp.ADD,
                nomeColab = "ANDERSON DA SILVA DELGADO",
                setMatric = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMatricColab = {},
                onNavObserv = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}