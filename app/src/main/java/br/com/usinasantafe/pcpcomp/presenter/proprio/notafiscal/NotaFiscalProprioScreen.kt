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
import br.com.usinasantafe.pcpcomp.ui.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton

const val TAG_NOTA_FISCAL_TEXT_FIELD_PROPRIO = "tag_nota_fiscal_text_field_proprio"

@Composable
fun NotaFiscalProprioScreen(
    viewModel: NotaFiscalViewModel,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalhe: () -> Unit,
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
                onNavDetalhe = onNavDetalhe,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getNotaFiscal()
        }
    }
}

@Composable
fun NotaFiscalProprioContent(
    notaFiscal: String?,
    flowApp: FlowApp,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit,
    onNavDetalhe: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_nota_fiscal))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp
            ),
            readOnly = true,
            value = if(notaFiscal.isNullOrEmpty()) "" else notaFiscal,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NOTA_FISCAL_TEXT_FIELD_PROPRIO)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
            flagUpdate = false
        )
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> onNavDestino()
                FlowApp.CHANGE -> onNavDetalhe()
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
            when (flowApp) {
                FlowApp.ADD -> onNavObserv()
                FlowApp.CHANGE -> onNavDetalhe()
            }
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
                onNavDetalhe = {},
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
                onNavDetalhe = {},
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
                onNavDetalhe = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}