package br.com.usinasantafe.pcpcomp.presenter.visitterc.placa

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
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

const val TAG_PLACA_TEXT_FIELD_VISIT_TERC = "tag_placa_text_field_visit_terc"

@Composable
fun PlacaVisitTercScreen(
    viewModel: PlacaVisitTercViewModel,
    onNavTipoVisitTerc: () -> Unit,
    onNavVeiculoVisitTerc: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PlacaVisitTercContent(
                flowApp = uiState.flowApp,
                placa = uiState.placa,
                onPlacaChanged = viewModel::onPlacaChanged,
                setPlaca = viewModel::setPlaca,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavTipoVisitTerc = onNavTipoVisitTerc,
                onNavVeiculoVisitTerc = onNavVeiculoVisitTerc,
                onNavDetalheVisitTerc = onNavDetalheVisitTerc,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverPlaca()
        }
    }
}

@Composable
fun PlacaVisitTercContent(
    flowApp: FlowApp,
    placa: String = "",
    onPlacaChanged: (String) -> Unit = {},
    setPlaca: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavTipoVisitTerc: () -> Unit,
    onNavVeiculoVisitTerc: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_placa))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = placa,
            onValueChange = onPlacaChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_PLACA_TEXT_FIELD_VISIT_TERC),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
            ),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
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
                        FlowApp.ADD -> onNavVeiculoVisitTerc()
                        FlowApp.CHANGE -> onNavDetalheVisitTerc()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.text_pattern_cancel))
            }
            Button(
                onClick = setPlaca,
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
                    stringResource(id = R.string.text_title_placa)
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
            onNavTipoVisitTerc()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PlacaVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlacaVisitTercContent(
                placa = "ABC-1234",
                flowApp = FlowApp.ADD,
                onPlacaChanged = {},
                setPlaca = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavTipoVisitTerc = {},
                onNavVeiculoVisitTerc = {},
                onNavDetalheVisitTerc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}