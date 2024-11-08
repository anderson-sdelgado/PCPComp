package br.com.usinasantafe.pcpcomp.presenter.initial.menuapont

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogCheckDesign
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TextSmallDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

@Composable
fun MenuApontScreen(
    viewModel: MenuApontViewModel,
    onNavMovVeicProprio: () -> Unit,
    onNavMovVeicVisitTerc: () -> Unit,
    onNavMovVeicResidencia: () -> Unit,
    onNavSplashScreen: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            MenuApontContent(
                descrVigia = uiState.descrVigia,
                descrLocal = uiState.descrLocal,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                closeAllMov = viewModel::closeAllMov,
                flagReturn = uiState.flagReturn,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMovVeicProprio = onNavMovVeicProprio,
                onNavMovVeicVisitTerc = onNavMovVeicVisitTerc,
                onNavMovVeicResidencia = onNavMovVeicResidencia,
                onNavSplashScreen = onNavSplashScreen,
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
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    closeAllMov: () -> Unit,
    flagReturn: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavMovVeicProprio: () -> Unit,
    onNavMovVeicVisitTerc: () -> Unit,
    onNavMovVeicResidencia: () -> Unit,
    onNavSplashScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TextSmallDesign(text = "VIGIA: $descrVigia")
        TextSmallDesign(text = "LOCAL: $descrLocal")
        TitleDesign(text = "MENU")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = "MOV. VEÍCULO PRÓPRIO",
                    setActionItem = onNavMovVeicProprio,
                    font = 24
                )
            }
            item {
                ItemListDesign(
                    text = "MOV. VEÍCULO VISITANTE/TERCEIRO",
                    setActionItem = onNavMovVeicVisitTerc,
                    font = 24
                )
            }
            item {
                ItemListDesign(
                    text = "MOV. VEÍCULO RESIDÊNCIA",
                    setActionItem = onNavMovVeicResidencia,
                    font = 24
                )
            }
        }
        Button(
            onClick = { setDialogCheck(true) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_out))
        }

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { setDialogCheck(false)  },
                setActionButtonOK = { closeAllMov() }
            )
        }

        if(flagReturn){
            onNavSplashScreen()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MenuApontPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialogCheck = false,
                setDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuApontPagePreviewShowDialog() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialogCheck = false,
                setDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                flagDialog = true,
                setCloseDialog = {},
                failure = "Failure Datasource",
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuApontPagePreviewShowDialogCheck() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MenuApontContent(
                descrVigia = "1975 - ANDERSON",
                descrLocal = "1 - USINA",
                flagDialogCheck = true,
                setDialogCheck = {},
                closeAllMov = {},
                flagReturn = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "Failure Datasource",
                onNavMovVeicProprio = {},
                onNavMovVeicVisitTerc = {},
                onNavMovVeicResidencia = {},
                onNavSplashScreen = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}