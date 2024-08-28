package br.com.usinasantafe.pcpcomp.presenter.proprio.destino

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

@Composable
fun DestinoProprioScreen(
    viewModel: DestinoProprioViewModel,
    onNavEquipSegList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DestinoProprioContent(
                destino = uiState.destino,
                flowApp = uiState.flowApp,
                onDestinoChanged = viewModel::onDestinoChanged,
                setDestino = viewModel::setDestino,
                typeMov = uiState.typeMov,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavEquipSegList = onNavEquipSegList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavNotaFiscal = onNavNotaFiscal,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DestinoProprioContent(
    destino: String,
    flowApp: FlowApp,
    onDestinoChanged: (String) -> Unit,
    setDestino: () -> Unit,
    typeMov: TypeMov,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavEquipSegList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "DESTINO")
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = destino,
            onValueChange = onDestinoChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    when (flowApp) {
                        FlowApp.ADD -> onNavEquipSegList()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = setDestino,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if (flagDialog) {
            val text = if(failure.isEmpty()) {
                stringResource(R.string.text_field_empty, "DESTINO")
            } else {
                stringResource(id = R.string.text_failure, failure)
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess) {
            when(typeMov){
                TypeMov.INPUT -> onNavObserv()
                TypeMov.OUTPUT -> onNavNotaFiscal()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DestinoProprioPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoProprioContent(
                destino = "Tabatinga",
                flowApp = FlowApp.ADD,
                onDestinoChanged = {},
                setDestino = {},
                typeMov = TypeMov.INPUT,
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavEquipSegList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DestinoProprioPagePreviewFieldEmpty() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoProprioContent(
                destino = "Tabatinga",
                flowApp = FlowApp.ADD,
                onDestinoChanged = {},
                setDestino = {},
                typeMov = TypeMov.INPUT,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "",
                onNavEquipSegList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DestinoProprioPagePreviewFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoProprioContent(
                destino = "Tabatinga",
                flowApp = FlowApp.ADD,
                onDestinoChanged = {},
                setDestino = {},
                typeMov = TypeMov.INPUT,
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavEquipSegList = {},
                onNavDetalheMovProprio = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}