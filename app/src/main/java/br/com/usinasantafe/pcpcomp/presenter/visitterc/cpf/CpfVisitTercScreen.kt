package br.com.usinasantafe.pcpcomp.presenter.visitterc.cpf

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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeButton

@Composable
fun CpfVisitTercScreen(
    viewModel: CpfVisitTercViewModel,
    onNavTipoVisitTerc: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
    onNavNomeVisitTerc: (String) -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            CpfVisitTercContent(
                flowApp = uiState.flowApp,
                cpf = uiState.cpf,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                currentProgress = uiState.currentProgress,
                setTextField = viewModel::setTextField,
                onNavTipoVisitTerc = onNavTipoVisitTerc,
                onNavDetalheVisitTerc = onNavDetalheVisitTerc,
                onNavNomeVisitTerc = onNavNomeVisitTerc,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun CpfVisitTercContent(
    flowApp: FlowApp,
    cpf: String,
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
    onNavTipoVisitTerc: () -> Unit,
    onNavDetalheVisitTerc: () -> Unit,
    onNavNomeVisitTerc: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_cpf))
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
            value = cpf,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = setTextField)
        BackHandler {
            when (flowApp) {
                FlowApp.ADD -> onNavTipoVisitTerc()
                FlowApp.CHANGE -> onNavDetalheVisitTerc()
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
            onNavNomeVisitTerc(cpf)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CpfVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CpfVisitTercContent(
                flowApp = FlowApp.ADD,
                cpf = "123.456.789-00",
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
                onNavTipoVisitTerc = {},
                onNavDetalheVisitTerc = {},
                onNavNomeVisitTerc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}