package br.com.usinasantafe.pcpcomp.presenter.visitterc.tipo

import androidx.activity.compose.BackHandler
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
import br.com.usinasantafe.pcpcomp.ui.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcpcomp.ui.theme.ItemListDesign
import br.com.usinasantafe.pcpcomp.ui.theme.PCPCompTheme
import br.com.usinasantafe.pcpcomp.ui.theme.TextButtonDesign
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

@Composable
fun TipoVisitTercScreen(
    viewModel: TipoVisitTercViewModel,
    onNavPlacaVisitTerc: () -> Unit,
    onNavCpfVisitTerc: () -> Unit,
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TipoVisitTercContent(
                setTipoVisitTerc = viewModel::setTypeVisitTerc,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavPlacaVisitTerc = onNavPlacaVisitTerc,
                onNavCpfVisitTerc = onNavCpfVisitTerc,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TipoVisitTercContent(
    setTipoVisitTerc: (TypeVisitTerc) -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    setCloseDialog: () -> Unit,
    failure: String,
    onNavPlacaVisitTerc: () -> Unit,
    onNavCpfVisitTerc: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(text = stringResource(id = R.string.text_title_tipo))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = "TERCEIRO",
                    setActionItem = { setTipoVisitTerc(TypeVisitTerc.TERCEIRO) },
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = "VISITANTE",
                    setActionItem = { setTipoVisitTerc(TypeVisitTerc.VISITANTE) },
                    font = 26
                )
            }
        }
        Button(
            onClick = onNavPlacaVisitTerc,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagAccess) {
            onNavCpfVisitTerc()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TipoVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TipoVisitTercContent(
                setTipoVisitTerc = {},
                flagAccess = false,
                flagDialog = false,
                setCloseDialog = {},
                failure = "",
                onNavPlacaVisitTerc = {},
                onNavCpfVisitTerc = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}