package br.com.usinasantafe.pcpcomp.presenter.local

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

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
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavNomeVigia = onNavNomeVigia,
                onNavMenuApont = onNavMenuApont,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.startRecoverLocals()
        }
    }
}

@Composable
fun LocalContent(
    locals: List<Local>,
    setIdLocal: (Long) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavNomeVigia: () -> Unit,
    onNavMenuApont: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "LOCAL")
        LazyColumn(
            modifier = Modifier
                .weight(1f),
        ) {
            items(locals) { local ->
                ItemListDesign(
                    text = local.descrLocal,
                    setActionItem = { setIdLocal(local.idLocal)  }
                )
            }
        }
        Button(
            onClick = onNavNomeVigia,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess){
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
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
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
                flagAccess = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure",
                onNavNomeVigia = {},
                onNavMenuApont = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}