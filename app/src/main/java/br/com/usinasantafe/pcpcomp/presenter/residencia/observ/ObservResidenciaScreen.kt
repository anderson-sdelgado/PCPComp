package br.com.usinasantafe.pcpcomp.presenter.residencia.observ

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
import br.com.usinasantafe.pcpcomp.presenter.proprio.observ.TAG_OBSERV_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

@Composable
fun ObservResidenciaScreen(
    viewModel: ObservResidenciaViewModel,
    onNavMotorista: () -> Unit,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ObservResidenciaContent(
                flowApp = uiState.flowApp,
                typeMov = uiState.typeMov,
                observ = uiState.observ,
                onObservChanged = viewModel::onObservChanged,
                setObserv = viewModel::setObserv,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMotorista = onNavMotorista,
                onNavMovEquipList = onNavMovEquipList,
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ObservResidenciaContent(
    flowApp: FlowApp,
    typeMov: TypeMov,
    observ: String,
    onObservChanged: (String) -> Unit,
    setObserv: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMotorista: () -> Unit,
    onNavMovEquipList: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_observ))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = observ,
            onValueChange = onObservChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_OBSERV_TEXT_FIELD_PROPRIO),
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
                        FlowApp.ADD -> {
                            when(typeMov){
                                TypeMov.INPUT -> onNavMotorista()
                                TypeMov.OUTPUT -> onNavMovEquipList()
                            }
                        }
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
                FlowApp.ADD -> onNavMovEquipList()
                FlowApp.CHANGE -> onNavDetalhe()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ObservResidenciaPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObservResidenciaContent(
                flowApp = FlowApp.ADD,
                typeMov = TypeMov.INPUT,
                observ = "Teste",
                onObservChanged = {},
                setObserv = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMotorista = {},
                onNavMovEquipList = {},
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}