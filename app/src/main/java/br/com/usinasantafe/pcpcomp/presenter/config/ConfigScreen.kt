package br.com.usinasantafe.pcpcomp.presenter.config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.porc

const val TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN = "tag_number_text_field_config_screen"
const val TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN = "tag_password_text_field_config_screen"

@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel,
    onNavMenuInicial: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ConfigContent(
                number = uiState.number,
                onNumberChanged = viewModel::updateNumber,
                password = uiState.password,
                onPasswordChanged = viewModel::updatePassword,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                currentProgress = uiState.currentProgress,
                onClickSaveAndUpdate = viewModel::saveDataAndUpdateAllDatabase,
                onNavMenuInicial = onNavMenuInicial,
                modifier = Modifier.padding(innerPadding),
            )
            viewModel.returnDataConfig()
        }
    }
}

@Composable
fun ConfigContent(
    number: String,
    onNumberChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    errors: Errors?,
    failure: String,
    msgProgress: String,
    currentProgress: Float,
    flagProgress: Boolean,
    onClickSaveAndUpdate: () -> Unit,
    onNavMenuInicial: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign("NRO APARELHO:")
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = number,
            onValueChange = onNumberChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        TitleDesign("SENHA:")
        OutlinedTextField(
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = onPasswordChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onClickSaveAndUpdate,
            modifier = Modifier.fillMaxWidth(),

        ) {
            TextButtonDesign(text = stringResource(id = R.string.texto_padrao_salvar_atualizar))
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onNavMenuInicial,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.texto_padrao_cancelar))
        }
        if (flagProgress) {
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = msgProgress,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        if(flagDialog) {
            if(errors != null){
                val text = when(errors!!){
                    Errors.FIELDEMPTY -> stringResource(id = R.string.texto_campo_vazio_config)
                    Errors.TOKEN -> stringResource(id = R.string.texto_recuperacao_token, failure)
                    Errors.UPDATE -> stringResource(id = R.string.texto_update_failure, failure)
                    Errors.EXCEPTION -> failure
                }
                AlertDialogSimpleDesign(
                    text = text,
                    setCloseDialog = setCloseDialog,
                )
            } else {
                AlertDialogSimpleDesign(
                    text = stringResource(id = R.string.texto_config_success),
                    setCloseDialog = setCloseDialog,
                    setActionButtonOK = onNavMenuInicial,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewProgress() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = false,
                setCloseDialog = {},
                errors = null,
                failure = "",
                flagProgress = true,
                msgProgress = "Enviando Dados de Token",
                currentProgress = porc(1f, 3f),
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ConfigPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = false,
                setCloseDialog = {},
                errors = null,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpenFieldEmpty() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpenFailureToken() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                errors = Errors.TOKEN,
                failure = "Failure Usecase",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpenFailureUpdate() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                errors = Errors.UPDATE,
                failure = "Failure Usecase",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
