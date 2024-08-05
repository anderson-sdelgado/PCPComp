package br.com.usinasantafe.pcpcomp.presenter.menuapont

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcpcomp.R
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListWithDetailDesign

@Composable
fun MenuApontScreen(
    viewModel: MenuApontViewModel,
    onNavMovVeicProprio: () -> Unit,
    onNavMovVeicVisitTerc: () -> Unit,
    onNavMovVeicResidencia: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuApontContent(
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMovVeicProprio = onNavMovVeicProprio,
                onNavMovVeicVisitTerc = onNavMovVeicVisitTerc,
                onNavMovVeicResidencia = onNavMovVeicResidencia,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.returnHeader()
        }
    }
}

@Composable
fun MenuApontContent(
    descrVigia: String,
    descrLocal: String,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMovVeicProprio: () -> Unit,
    onNavMovVeicVisitTerc: () -> Unit,
    onNavMovVeicResidencia: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        TitleListWithDetailDesign(text = "MENU")
        LazyColumn {
            item {
                ItemListDesign(
                    text = "MOV. VEÍCULO PRÓPRIO",
                    setActionItem = onNavMovVeicProprio
                )
            }
            item {
                ItemListDesign(
                    text = "MOV. VEÍCULO VISITANTE/TERCEIRO",
                    setActionItem = onNavMovVeicVisitTerc
                )
            }
            item {
                ItemListDesign(
                    "MOV. VEÍCULO RESIDÊNCIA",
                    setActionItem = onNavMovVeicResidencia
                )
            }
        }

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewShowDialog() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Datasource",
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}