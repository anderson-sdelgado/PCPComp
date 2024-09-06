package br.com.usinasantafe.pcpcomp.presenter.proprio.nroequip

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
import br.com.usinasantafe.pcpcomp.utils.TypeEquip

const val TAG_NUMBER_TEXT_FIELD_NRO_EQUIP_SCREEN = "tag_number_text_field_nro_equip_screen"

@Composable
fun NroEquipScreen(
    viewModel: NroEquipProprioViewModel,
    onNavMovProprioList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavEquipSegList: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NroEquipContent(
                nroEquip = uiState.nroEquip,
                flowApp = uiState.flowApp,
                typeEquip = uiState.typeEquip,
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
                onNavMovProprioList = onNavMovProprioList,
                onNavDetalheMovProprio = onNavDetalheMovProprio,
                onNavEquipSegList = onNavEquipSegList,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.getNroEquip()
        }
    }
}

@Composable
fun NroEquipContent(
    nroEquip: String,
    flowApp: FlowApp,
    typeEquip: TypeEquip,
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
    onNavMovProprioList: () -> Unit,
    onNavDetalheMovProprio: () -> Unit,
    onNavEquipSegList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_nro_equip))
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
            value = nroEquip,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_NUMBER_TEXT_FIELD_NRO_EQUIP_SCREEN)
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(setActionButton = setTextField)
        BackHandler {
            when (typeEquip) {
                TypeEquip.VEICULO -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavMovProprioList()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                }

                TypeEquip.VEICULOSEG -> onNavEquipSegList()
            }
        }


        if (flagDialog) {
            val text = if (flagFailure) {
                when (errors) {
                    Errors.FIELDEMPTY -> stringResource(
                        id = R.string.text_field_empty,
                        stringResource(id = R.string.text_title_nro_equip)
                    )

                    Errors.UPDATE -> stringResource(id = R.string.text_update_failure, failure)
                    Errors.TOKEN,
                    Errors.EXCEPTION -> stringResource(id = R.string.text_failure, failure)

                    Errors.INVALID -> stringResource(
                        id = R.string.text_input_data_invalid,
                        stringResource(id = R.string.text_title_nro_equip)
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
            when (typeEquip) {
                TypeEquip.VEICULO -> {
                    when (flowApp) {
                        FlowApp.ADD -> onNavEquipSegList()
                        FlowApp.CHANGE -> onNavDetalheMovProprio()
                    }
                }

                TypeEquip.VEICULOSEG -> onNavEquipSegList()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
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
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewFieldEmpty() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
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
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
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
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewUpdate() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
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
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewBlockedAccess() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
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
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NroEquipPagePreviewUpdated() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NroEquipContent(
                nroEquip = "",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
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
                onNavMovProprioList = {},
                onNavDetalheMovProprio = {},
                onNavEquipSegList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
