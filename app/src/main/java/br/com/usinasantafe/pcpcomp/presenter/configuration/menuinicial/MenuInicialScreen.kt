package br.com.usinasantafe.pcpcomp.presenter.configuration.menuinicial

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.BuildConfig
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun MenuInicialScreen(
    viewModel: MenuInicialViewModel,
    onNavMatricVigia: () -> Unit,
    onNavSenha: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuInicialContent(
                modifier = Modifier.padding(innerPadding),
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                flagFailure = uiState.flagFailure,
                failure = uiState.failure,
                flagAccess = uiState.flagAccess,
                onCheckAccess = viewModel::checkAccess,
                onNavMatricVigia,
                onNavSenha
            )
        }
    }
}

@Composable
fun MenuInicialContent(
    modifier: Modifier = Modifier,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    flagFailure: Boolean,
    failure: String,
    flagAccess: Boolean,
    onCheckAccess: () -> Unit,
    onNavMatricVigia: () -> Unit,
    onNavSenha: () -> Unit
) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = "MENU INICIAL - V ${BuildConfig.VERSION_NAME}")
        LazyColumn {
            item {
                ItemListDesign(
                    text = "APONTAMENTO",
                    setActionItem = onCheckAccess
                )
            }
            item {
                ItemListDesign(
                    text = "CONFIGURAÇÃO",
                    setActionItem = onNavSenha
                )
            }
            item {
                ItemListDesign(
                    "SAIR",
                    setActionItem = {
                        activity?.finish()
                    }
                )
            }
        }
        BackHandler {}

        if(flagDialog) {
            val text = if(!flagFailure) stringResource(id = R.string.text_blocked_access_app) else stringResource(id = R.string.text_failure, failure)
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagAccess) {
            onNavMatricVigia()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                modifier = Modifier.padding(innerPadding),
                flagDialog = false,
                setCloseDialog = {},
                flagFailure = false,
                flagAccess = false,
                failure = "",
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreviewOpenDialog() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                modifier = Modifier.padding(innerPadding),
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = false,
                flagAccess = false,
                failure = "",
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicialPagePreviewOpenDialogFailure() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuInicialContent(
                modifier = Modifier.padding(innerPadding),
                flagDialog = true,
                setCloseDialog = {},
                flagFailure = true,
                flagAccess = false,
                failure = "Failure Datasource",
                onCheckAccess = {},
                onNavMatricVigia = {},
                onNavSenha = {}
            )
        }
    }
}