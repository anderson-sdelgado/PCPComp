package br.com.usinasantafe.pcpcomp.presenter.visitterc.destino

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
import br.com.usinasantafe.pcpcomp.presenter.proprio.destino.TAG_DESTINO_TEXT_FIELD_PROPRIO
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

const val TAG_DESTINO_TEXT_FIELD_VISIT_TERC = "tag_destino_text_field_visit_terc"

@Composable
fun DestinoVisitTercScreen(
    viewModel: DestinoVisitTercViewModel,
    onNavPassagList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DestinoVisitTercContent(
                flowApp = uiState.flowApp,
                destino = uiState.destino,
                onDestinoChanged = viewModel::onDestinoChanged,
                setDestino = viewModel::setDestino,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavPassagList = onNavPassagList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DestinoVisitTercContent(
    flowApp: FlowApp,
    destino: String,
    onDestinoChanged: (String) -> Unit,
    setDestino: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavPassagList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavObserv: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_destino))
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = destino,
            onValueChange = onDestinoChanged,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag(TAG_DESTINO_TEXT_FIELD_VISIT_TERC),
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
                        FlowApp.ADD -> onNavPassagList()
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
            val text = if (failure.isEmpty()) {
                stringResource(
                    R.string.text_field_empty,
                    stringResource(id = R.string.text_title_destino)
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
            onNavObserv()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DestinoVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DestinoVisitTercContent(
                flowApp = FlowApp.ADD,
                destino = "Tabatinga",
                onDestinoChanged = {},
                setDestino = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavPassagList = {},
                onNavDetalheMovProprio = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}