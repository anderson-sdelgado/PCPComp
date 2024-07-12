package br.com.usinasantafe.pcpcomp.presenter.senha

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
                statusDialog = uiState.statusDialog,
                setCloseDialog = viewModel::setCloseDialog,
                statusAccess = uiState.statusAccess,
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
    statusDialog: Boolean,
    setCloseDialog: () -> Unit,
    statusAccess: Boolean,
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

        if(statusDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.texto_senha_invalida),
                setCloseDialog = setCloseDialog,
                setOkDialog = {}
            )
        }

        if(statusAccess) {
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
                statusDialog = false,
                setCloseDialog = {},
                statusAccess = false,
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
                statusDialog = true,
                setCloseDialog = {},
                statusAccess = false,
                onCheckAccess = {},
                onNavMenuInicial = {},
                onNavConfig = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}