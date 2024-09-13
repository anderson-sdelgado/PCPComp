package br.com.usinasantafe.pcpcomp.presenter.visitterc.veiculo

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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp

const val TAG_VEICULO_TEXT_FIELD_VISIT_TERC = "tag_veiculo_text_field_visit_terc"

@Composable
fun VeiculoVisitTercScreen(
    viewModel: VeiculoVisitTercViewModel,
    onNavPlacaVisitTerc: () -> Unit,
    onNavMovEquipVisitTercList: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            VeiculoVisitTercContent(
                flowApp = uiState.flowApp,
                veiculo = uiState.veiculo,
                onVeiculoChanged = viewModel::onVeiculoChanged,
                setVeiculo = viewModel::setVeiculo,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMovEquipVisitTercList = onNavMovEquipVisitTercList,
                onNavDetalheVisitTerc = onNavDetalheVisitTerc,
                onNavPlacaVisitTerc = onNavPlacaVisitTerc,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverVeiculo()
        }
    }
}

@Composable
fun VeiculoVisitTercContent(
    flowApp: FlowApp,
    veiculo: String = "",
    onVeiculoChanged: (String) -> Unit = {},
    setVeiculo: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMovEquipVisitTercList: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
    onNavPlacaVisitTerc: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_veiculo))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = veiculo,
            onValueChange = onVeiculoChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_VEICULO_TEXT_FIELD_VISIT_TERC),
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
                        FlowApp.ADD -> onNavMovEquipVisitTercList()
                        FlowApp.CHANGE -> onNavDetalheVisitTerc()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = setVeiculo,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_ok))
            }
        }
        BackHandler {}

        if (flagDialog) {
            val text = if (failure.isEmpty()) {
                stringResource(
                    R.string.text_field_empty,
                    stringResource(id = R.string.text_title_veiculo)
                )
            } else {
                stringResource(id = R.string.text_failure, failure)
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
            onNavPlacaVisitTerc()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun VeiculoVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            VeiculoVisitTercContent(
                veiculo = "GOL",
                flowApp = FlowApp.ADD,
                onVeiculoChanged = {},
                setVeiculo = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMovEquipVisitTercList = {},
                onNavDetalheVisitTerc = {},
                onNavPlacaVisitTerc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}