package br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe

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
fun DetalheVisitTercScreen(
    viewModel: DetalheVisitTercViewModel,
    onNavMovEquipEditList: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavPlaca: () -> Unit,
    onNavCpf: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DetalheVisitTercContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                veiculo = uiState.veiculo,
                placa = uiState.placa,
                tipoVisitTerc = uiState.tipoVisitTerc,
                motorista = uiState.motorista,
                passageiro = uiState.passageiro,
                destino = uiState.destino,
                observ = uiState.observ,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavVeiculo = onNavVeiculo,
                onNavPlaca = onNavPlaca,
                onNavCpf = onNavCpf,
                onNavPassagList = onNavPassagList,
                onNavDestino = onNavDestino,
                onNavObserv = onNavObserv,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                setCloseDialog = viewModel::setCloseDialog,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverDetalhe()
        }
    }
}

@Composable
fun DetalheVisitTercContent(
    dthr: String,
    tipoMov: String,
    veiculo: String,
    placa: String,
    tipoVisitTerc: String,
    motorista: String,
    passageiro: String,
    destino: String,
    observ: String,
    flagDialog: Boolean,
    failure: String,
    setCloseDialog: () -> Unit,
    onNavMovEquipEditList: () -> Unit,
    onNavVeiculo: () -> Unit,
    onNavPlaca: () -> Unit,
    onNavCpf: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
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
                    text = tipoVisitTerc,
                    setActionItem = {}
                )
            }
            item {
                ItemListDesign(
                    text = motorista,
                    setActionItem = onNavCpf
                )
            }
            item {
                ItemListDesign(
                    text = passageiro,
                    setActionItem = onNavPassagList
                )
            }
            item {
                ItemListDesign(
                    text = destino,
                    setActionItem = onNavDestino
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
fun DetalheVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheVisitTercContent(
                dthr = "DATA/HORA: 29/08/2024 16:14",
                tipoMov = "ENTRADA",
                veiculo = "VEÍCULO: 100",
                placa = "PLACA: ABC-1234",
                tipoVisitTerc = "VISITANTE",
                motorista = "MOTORISTA: 123.456.789-00 - ANDERSON DA SILVA DELGADO",
                passageiro = "PASSAGEIRO(S): 123.456.789-00 - JOSE DONIZETE;",
                destino = "DESTINO: TESTE DESTINO",
                observ = "OBSERVAÇÃO: TESTE OBSERVAÇÃO",
                onNavMovEquipEditList = {},
                onNavVeiculo = {},
                onNavPlaca = {},
                onNavCpf = {},
                onNavPassagList = {},
                onNavDestino = {},
                onNavObserv = {},
                flagDialog = false,
                failure = "",
                setCloseDialog = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}