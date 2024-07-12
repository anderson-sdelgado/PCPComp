package br.com.usinasantafe.pcpcomp.presenter.config

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.presenter.senha.SenhaViewModel
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import org.koin.androidx.compose.koinViewModel

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
                statusDialog = uiState.statusDialog,
                setCloseDialog = viewModel::setCloseDialog,
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
    statusDialog: Boolean,
    setCloseDialog: () -> Unit,
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
            value = number,
            onValueChange = onNumberChanged,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        TitleDesign("SENHA:")
        OutlinedTextField(
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

        if(statusDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.texto_config_invalida),
                setCloseDialog = setCloseDialog,
                setOkDialog = {}
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
                statusDialog = false,
                setCloseDialog = {},
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigPagePreviewDialogOpen() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ConfigContent(
                number = "",
                onNumberChanged = {},
                password = "",
                onPasswordChanged = {},
                statusDialog = true,
                setCloseDialog = {},
                onClickSaveAndUpdate = {},
                onNavMenuInicial = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}