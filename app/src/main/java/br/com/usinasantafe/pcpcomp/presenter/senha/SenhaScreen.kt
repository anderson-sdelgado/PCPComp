package br.com.usinasantafe.pcpcomp.presenter.senha

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

const val TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN = "tag_password_text_field_senha_screen"

@Composable
fun SenhaScreen(
    viewModel: SenhaViewModel,
    onNavMenuInicial: () -> Unit,
    onNavConfig: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SenhaContent(
                password = uiState.password,
                onPasswordChanged = viewModel::updatePassword,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                flagAccess = uiState.flagAccess,
                flagFailure = uiState.flagFailure,
                onCheckAccess = viewModel::checkPassword,
                onNavMenuInicial = onNavMenuInicial,
                onNavConfig = onNavConfig,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Composable
fun SenhaContent(
    password: String,
    onPasswordChanged: (String) -> Unit,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    failure: String,
    flagAccess: Boolean,
    onCheckAccess: () -> Unit,
    onNavMenuInicial: () -> Unit,
    onNavConfig: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign("SENHA:")
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            value = password,
            onValueChange = onPasswordChanged,
            modifier = Modifier.fillMaxWidth().testTag(TAG_PASSWORD_TEXT_FIELD_SENHA_SCREEN)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        )  {
            Button(
                onClick = onCheckAccess,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(text = stringResource(id = R.string.texto_padrao_ok))
            }
            Button(
                onClick = onNavMenuInicial,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(text = stringResource(id = R.string.texto_padrao_cancelar))
            }
        }
        BackHandler {}

        if(flagDialog) {
            val text = if(!flagFailure) stringResource(id = R.string.texto_senha_invalida) else failure
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess) {
            onNavConfig()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SenhaPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SenhaContent(
                password = "",
                onPasswordChanged = {},
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                failure = "",
                flagAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SenhaPagePreviewMsgOpen() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SenhaContent(
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                failure = "",
                flagAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SenhaPagePreviewMsgOpenFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SenhaContent(
                password = "",
                onPasswordChanged = {},
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                failure = "Error CheckPasswordConfig",
                flagAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}