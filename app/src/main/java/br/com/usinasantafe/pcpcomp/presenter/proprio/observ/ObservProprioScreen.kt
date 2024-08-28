package br.com.usinasantafe.pcpcomp.presenter.proprio.observ

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
fun ObservProprioScreen(
    viewModel: ObservProprioViewModel,
    onNavDestinoProprio: () -> Unit,
    onNavNotaFiscalProprio: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavMovEquipProprioList: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ObservProprioContent(
                observ = uiState.observ,
                flowApp = uiState.flowApp,
                onObservChanged = viewModel::onObservChanged,
                setObserv = viewModel::setObserv,
                typeMov = uiState.typeMov,
                setReturn = viewModel::setReturn,
                flagAccess = uiState.flagAccess,
                flagReturn = uiState.flagReturn,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavDestinoProprio = onNavDestinoProprio,
                onNavNotaFiscalProprio = onNavNotaFiscalProprio,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavMovEquipProprioList = onNavMovEquipProprioList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ObservProprioContent(
    observ: String,
    flowApp: FlowApp,
    onObservChanged: (String) -> Unit,
    setObserv: () -> Unit,
    typeMov: TypeMov,
    setReturn: () -> Unit,
    flagAccess: Boolean,
    flagReturn: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavDestinoProprio: () -> Unit,
    onNavNotaFiscalProprio: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavMovEquipProprioList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "OBSERVAÇÃO")
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = observ,
            onValueChange = onObservChanged,
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
                        FlowApp.ADD -> setReturn()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = setObserv,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if(flagDialog){
            val text = stringResource(id = R.string.text_failure, failure)
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess){
            when(flowApp){
                FlowApp.ADD -> onNavMovEquipProprioList()
                FlowApp.CHANGE -> onNavDetalheMovProprio()
            }
        }

        if(flagReturn){
            when(typeMov){
                TypeMov.INPUT -> onNavDestinoProprio()
                TypeMov.OUTPUT -> onNavNotaFiscalProprio()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ObservProprioPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObservProprioContent(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                onObservChanged = {},
                setObserv = {},
                typeMov = TypeMov.INPUT,
                setReturn = {},
                flagAccess = false,
                flagReturn = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavDestinoProprio = {},
                onNavNotaFiscalProprio = {},
                onNavDetalheMovProprio = {},
                onNavMovEquipProprioList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ObservProprioPagePreviewWithFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObservProprioContent(
                observ = "Teste",
                flowApp = FlowApp.ADD,
                onObservChanged = {},
                setObserv = {},
                typeMov = TypeMov.INPUT,
                setReturn = {},
                flagAccess = false,
                flagReturn = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Usecase",
                onNavDestinoProprio = {},
                onNavNotaFiscalProprio = {},
                onNavDetalheMovProprio = {},
                onNavMovEquipProprioList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
