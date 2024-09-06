package br.com.usinasantafe.pcpcomp.presenter.residencia.detalhe

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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleListDesign

@Composable
fun DetalheResidenciaScreen(
    viewModel: DetalheResidenciaViewModel,
    onNavMovEquipEditList: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavPlaca: () -> Unit,
    onNavMotorista: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DetalheResidenciaContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                veiculo = uiState.veiculo,
                placa = uiState.placa,
                motorista = uiState.motorista,
                observ = uiState.observ,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                setCloseDialog = viewModel::setCloseDialog,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavVeiculo = onNavVeiculo,
                onNavPlaca = onNavPlaca,
                onNavMotorista = onNavMotorista,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun DetalheResidenciaContent(
    dthr: String,
    tipoMov: String,
    veiculo: String,
    placa: String,
    motorista: String,
    observ: String,
    flagDialog: Boolean,
    failure: String,
    setCloseDialog: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavPlaca: () -> Unit,
    onNavMotorista: () -> Unit,
    onNavObserv: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleListDesign(text = stringResource(id = R.string.text_title_detalhe))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = dthr,
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = tipoMov,
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = veiculo,
                    setActionItem = onNavVeiculo,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = placa,
                    setActionItem = onNavPlaca,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = motorista,
                    setActionItem = onNavMotorista
                )
            }
            item {
                ItemListDesign(
                    text = observ,
                    setActionItem = onNavObserv
                )
            }
        }
        Button(
            onClick = onNavMovEquipEditList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavMovEquipEditList() }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalheResidenciaPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheResidenciaContent(
                dthr = "",
                tipoMov = "",
                veiculo = "",
                placa = "",
                motorista = "",
                observ = "",
                flagDialog = false,
                failure = "",
                setCloseDialog = {},
                onNavMovEquipEditList = {},
                onNavVeiculo = {},
                onNavPlaca = {},
                onNavMotorista = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}