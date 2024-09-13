package br.com.usinasantafe.pcpcomp.presenter.visitterc.nome

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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun NomeVisitTercScreen(
    viewModel: NomeVisitTercViewModel,
    onNavCpfVisitTerc: () -> Unit,
    onNavPassagVisitTerc: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NomeVisitTercContent(
                tipo = uiState.tipo,
                nome = uiState.nome,
                empresa = uiState.empresa,
                setCPF = viewModel::setCPF,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavCpfVisitTerc = onNavCpfVisitTerc,
                onNavPassagVisitTerc = onNavPassagVisitTerc,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnNome()
        }
    }
}

@Composable
fun NomeVisitTercContent(
    tipo: String,
    nome: String,
    empresa: String,
    setCPF: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavCpfVisitTerc: () -> Unit,
    onNavPassagVisitTerc: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "NOME DO $tipo")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = nome,
                fontSize = 26.sp,
            )
        }
        TitleListDesign(text = "EMPRESA(S) DO $tipo")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = empresa,
                fontSize = 26.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = onNavCpfVisitTerc,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = setCPF,
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
                setActionButtonOK = { onNavCpfVisitTerc() }
            )
        }

        if(flagAccess) {
            onNavPassagVisitTerc()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NomeVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NomeVisitTercContent(
                tipo = "TERCEIRO",
                nome = "NOME TESTE",
                empresa = "EMPRESA TESTE",
                setCPF = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavCpfVisitTerc = {},
                onNavPassagVisitTerc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}