package br.com.usinasantafe.pcpcomp.presenter.initial.local

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogProgressDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.Errors

@Composable
fun LocalScreen(
    viewModel: LocalViewModel,
    onNavNomeVigia: () -> Unit,
    onNavMenuApont: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            LocalContent(
                locals = uiState.locals,
                setIdLocal = viewModel::setIdLocal,
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
                onNavNomeVigia = onNavNomeVigia,
                onNavMenuApont = onNavMenuApont,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.localList()
        }
    }
}

@Composable
fun LocalContent(
    locals: List<Local>,
    setIdLocal: (Int) -> Unit,
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
    onNavNomeVigia: () -> Unit,
    onNavMenuApont: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = "LOCAL")
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(locals) { local ->
                ItemListDesign(
                    text = local.descrLocal,
                    setActionItem = { setIdLocal(local.idLocal) },
                    font = 26
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
            onClick = onNavNomeVigia,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
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
            onNavMenuApont()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LocalContent(
                locals = listOf(
                    Local(
                        idLocal = 1,
                        "USINA"
                    ),
                    Local(
                        idLocal = 2,
                        "MOTO"
                    ),
                    Local(
                        idLocal = 3,
                        "RESIDÊNCIA"
                    ),
                ),
                setIdLocal = {},
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
                onNavNomeVigia = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewShowAlertDialog() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LocalContent(
                locals = listOf(
                    Local(
                        idLocal = 1,
                        "USINA"
                    ),
                    Local(
                        idLocal = 2,
                        "MOTO"
                    ),
                    Local(
                        idLocal = 3,
                        "RESIDÊNCIA"
                    ),
                ),
                setIdLocal = {},
                updateDatabase = {},
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                errors = Errors.FIELDEMPTY,
                failure = "",
                flagProgress = false,
                msgProgress = "",
                currentProgress = 0.0f,
                onNavNomeVigia = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}