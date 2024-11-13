package br.com.usinasantafe.pcpcomp.presenter.proprio.matriccolab

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
import br.com.usinasantafe.pcpcomp.presenter.initial.matricvigia.TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogProgressDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ButtonsGenericNumeric
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante

@Composable
fun MatricColabScreen(
    viewModel: MatricColabViewModel,
    onNavMovVeicProprio: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNomeColab: (String) -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MatricColabContent(
                flowApp = uiState.flowApp,
                matricColab = uiState.matricColab,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                currentProgress = uiState.currentProgress,
                onNavMovVeicProprio = onNavMovVeicProprio,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavNomeColab = onNavNomeColab,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getMatricColab()
        }
    }
}

@Composable
fun MatricColabContent(
    flowApp: FlowApp,
    matricColab: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    msgProgress: String,
    currentProgress: Float,
    onNavMovVeicProprio: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavNomeColab: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_matric_colab))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp,
            ),
            readOnly = true,
            value = matricColab,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = setTextField)
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> onNavMovVeicProprio()
                FlowApp.CHANGE -> onNavDetalheMovProprio()
            }
        }

        if (flagDialog) {
            val text = if (flagFailure) {
                when (errors) {
                    Errors.FIELDEMPTY -> stringResource(
                        id = R.string.text_field_empty,
                        stringResource(id = R.string.text_title_matric_colab)
                    )

                    Errors.UPDATE -> stringResource(id = R.string.text_update_failure, failure)
                    Errors.TOKEN,
                    Errors.EXCEPTION -> stringResource(id = R.string.text_failure, failure)

                    Errors.INVALID -> stringResource(
                        id = R.string.text_input_data_invalid,
                        stringResource(id = R.string.text_title_matric_colab)
                    )
                }
            } else {
                msgProgress
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            AlertDialogProgressDesign(
                currentProgress = currentProgress,
                msgProgress = msgProgress
            )
        }

        if (flagAccess) {
            onNavNomeColab(matricColab)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavMovVeicProprio = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewFieldEmpty() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                matricColab = "",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavMovVeicProprio = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                matricColab = "",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.EXCEPTION,
                failure = "Datasource Failure",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavMovVeicProprio = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewFailureUpdate() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                matricColab = "",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.UPDATE,
                failure = "Datasource Failure",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavMovVeicProprio = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewBlockedAccess() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                matricColab = "",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.INVALID,
                failure = "Datasource Failure",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavMovVeicProprio = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricColabPagePreviewUpdate() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricColabContent(
                matricColab = "",
                flowApp = FlowApp.ADD,
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.INVALID,
                failure = "Datasource Failure",
                flagProgress = true,
                msgProgress = "Atualizando dados",
                currentProgress = 0.3333334f,
                onNavMovVeicProprio = {},
                onNavDetalheMovProprio = {},
                onNavNomeColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}