package br.com.usinasantafe.pcpcomp.presenter.proprio.notafiscal

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ButtonsGenericNumericWithoutUpdate
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButtonWithoutUpdate

const val TAG_NOTA_FISCAL_TEXT_FIELD_PROPRIO = "tag_nota_fiscal_text_field_proprio"

@Composable
fun NotaFiscalProprioScreen(
    viewModel: NotaFiscalViewModel,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NotaFiscalProprioContent(
                notaFiscal = uiState.notaFiscal,
                flowApp = uiState.flowApp,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavDestino = onNavDestino,
                onNavObserv = onNavObserv,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun NotaFiscalProprioContent(
    notaFiscal: String,
    flowApp: FlowApp,
    setTextField: (String, TypeButtonWithoutUpdate) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_nota_fiscal))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 24.sp
            ),
            readOnly = true,
            value = notaFiscal,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NOTA_FISCAL_TEXT_FIELD_PROPRIO)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumericWithoutUpdate(setActionButton = setTextField)
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> onNavDestino()
                FlowApp.CHANGE -> onNavDetalheMovProprio()
            }
        }

        if(flagDialog){
            val text = stringResource(id = R.string.text_failure, failure)
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
fun NotaFiscalProprioPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                notaFiscal = "",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavDestino = {},
                onNavObserv = {},
                onNavDetalheMovProprio = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotaFiscalProprioPagePreviewWithValue() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavDestino = {},
                onNavObserv = {},
                onNavDetalheMovProprio = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotaFiscalProprioPagePreviewWithFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NotaFiscalProprioContent(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Repository",
                onNavDestino = {},
                onNavObserv = {},
                onNavDetalheMovProprio = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}