package br.com.usinasantafe.pcpcomp.presenter.chave.chavelist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogProgressDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.Errors

const val TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN = "tag_filter_text_field_chave_list_screen"

@Composable
fun ChaveListScreen(
    viewModel: ChaveListViewModel,
    onNavMenuControleList: () -> Unit,
    onNavMatricColab: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ChaveListContent(
                chaveList = uiState.chaveList,
                setIdChave = viewModel::setIdChave,
                field = uiState.field,
                onFieldChanged = viewModel::onFieldChanged,
                updateDatabase = viewModel::updateDatabase,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                failure = uiState.failure,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                currentProgress = uiState.currentProgress,
                onNavMatricColab = onNavMatricColab,
                onNavMenuControleList = onNavMenuControleList,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverList()
        }
    }
}

@Composable
fun ChaveListContent(
    chaveList: List<ChaveModel>,
    setIdChave: (Int) -> Unit,
    field: String,
    onFieldChanged: (String) -> Unit,
    updateDatabase: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    errors: Errors,
    failure: String,
    flagProgress: Boolean,
    msgProgress: String,
    currentProgress: Float,
    onNavMenuControleList: () -> Unit,
    onNavMatricColab: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            ),
            value = field,
            onValueChange = onFieldChanged,
            placeholder = {
                Text("Pesquisa")
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TAG_FILTER_TEXT_FIELD_CHAVE_LIST_SCREEN)
        )
        TitleDesign(
            text = stringResource(id = R.string.text_title_list_key)
        )
        Spacer(modifier = Modifier.padding(vertical = 6.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(chaveList) {
                ItemListDesign(
                    text = it.descr,
                    setActionItem = {
                        setIdChave(it.id)
                    },
                    id = it.id,
                    padding = 6
                )
            }
        }
        Button(
            onClick = updateDatabase,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_update))
        }
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        Button(
            onClick = onNavMenuControleList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}

        if (flagDialog) {
            val text = if (flagFailure) {
                when(errors) {
                    Errors.UPDATE -> stringResource(
                        id = R.string.text_update_failure,
                        failure
                    )
                    else -> stringResource(
                        id = R.string.text_failure,
                        failure
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
            onNavMatricColab()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ChaveListPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ChaveListContent(
                chaveList = listOf(
                    ChaveModel(
                        1,
                        "01 - SALA TI - TI"
                    ),
                    ChaveModel(
                        2,
                        "02 - SERVIDOR TI - TI"
                    )
                ),
                setIdChave = {},
                field = "",
                onFieldChanged = {},
                updateDatabase = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavMenuControleList = {},
                onNavMatricColab = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}