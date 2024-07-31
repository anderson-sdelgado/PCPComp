package br.com.usinasantafe.pcpcomp.presenter.matricvigia

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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TypeButton

const val TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN = "tag_number_text_field_matric_vigia_screen"

@Composable
fun MatricVigiaScreen(
    viewModel: MatricVigiaViewModel,
    onNavMenuInicial: () -> Unit,
    onNavNomeVigia: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MatricVigiaContent(
                matricVigia = uiState.matricVigia,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                onNavMenuInicial = onNavMenuInicial,
                onNavNomeVigia = onNavNomeVigia,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun MatricVigiaContent(
    matricVigia: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    onNavMenuInicial: () -> Unit,
    onNavNomeVigia: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "MATRICULA VIGIA")
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
            value = matricVigia,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_MATRIC_VIGIA_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = setTextField)
        BackHandler {
            onNavMenuInicial()
        }

        if(flagDialog){
            val text = if(flagFailure){
                val text = when(errors){
                    Errors.FIELDEMPTY -> stringResource(id = R.string.text_field_empty, "MATRICULA VIGIA")
                    Errors.UPDATE -> stringResource(id = R.string.text_update_failure, failure)
                    Errors.TOKEN,
                    Errors.EXCEPTION -> failure
                }
                text
            } else {
                stringResource(id = R.string.text_input_data_invalid, "MATRICULA VIGIA")
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess) {
            onNavNomeVigia()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                matricVigia = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreviewFieldEmpty() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                matricVigia = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.FIELDEMPTY,
                failure = "",
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreviewFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                matricVigia = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.EXCEPTION,
                failure = "Datasource Failure",
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreviewFailureUpdate() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                matricVigia = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                errors = Errors.UPDATE,
                failure = "Datasource Failure",
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatricVigiaPagePreviewBlockedAccess() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MatricVigiaContent(
                matricVigia = "",
                setTextField = { _, _ -> },
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.EXCEPTION,
                failure = "",
                onNavMenuInicial = {},
                onNavNomeVigia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}