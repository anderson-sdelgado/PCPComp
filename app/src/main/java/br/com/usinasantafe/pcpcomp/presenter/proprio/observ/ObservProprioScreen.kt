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
import androidx.compose.ui.platform.testTag
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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

const val TAG_OBSERV_TEXT_FIELD_PROPRIO = "tag_observ_text_field_proprio"

@Composable
fun ObservProprioScreen(
    viewModel: ObservProprioViewModel,
    onNavDestino: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMovList: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ObservProprioContent(
                flowApp = uiState.flowApp,
                typeMov = uiState.typeMov,
                observ = uiState.observ,
                onObservChanged = viewModel::onObservChanged,
                setObserv = viewModel::setObserv,
                setReturn = viewModel::setReturn,
                flagAccess = uiState.flagAccess,
                flagReturn = uiState.flagReturn,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavDestino = onNavDestino,
                onNavNotaFiscal = onNavNotaFiscal,
                onNavDetalhe = onNavDetalhe,
                onNavMovList = onNavMovList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ObservProprioContent(
    flowApp: FlowApp,
    typeMov: TypeMov,
    observ: String?,
    onObservChanged: (String) -> Unit,
    setObserv: () -> Unit,
    setReturn: () -> Unit,
    flagAccess: Boolean,
    flagReturn: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavDestino: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavDetalhe: () -> Unit,
    onNavMovList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_observ))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = if(observ.isNullOrEmpty()) "" else observ,
            onValueChange = onObservChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_OBSERV_TEXT_FIELD_PROPRIO),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 28.sp
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
                        FlowApp.CHANGE -> onNavDetalhe()
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
                FlowApp.ADD -> onNavMovList()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }

        if(flagReturn){
            when(typeMov){
                TypeMov.INPUT -> onNavDestino()
                TypeMov.OUTPUT -> onNavNotaFiscal()
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
                onNavDestino = {},
                onNavNotaFiscal = {},
                onNavDetalhe = {},
                onNavMovList = {},
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
                onNavDestino = {},
                onNavNotaFiscal = {},
                onNavDetalhe = {},
                onNavMovList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
