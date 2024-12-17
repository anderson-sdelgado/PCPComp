package br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import br.com.usinasantafe.pcpcomp.ui.theme.TitleDesign

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
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                setCloseDialog = viewModel::setCloseDialog,
                closeMov = viewModel::closeMov,
                flagCloseMov = uiState.flagCloseMov,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                onNavMovEquipEditList = onNavMovEquipEditList,
                onNavVeiculo = onNavVeiculo,
                onNavPlaca = onNavPlaca,
                onNavCpf = onNavCpf,
                onNavPassagList = onNavPassagList,
                onNavDestino = onNavDestino,
                onNavObserv = onNavObserv,
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
    destino: String?,
    observ: String?,
    flagDialog: Boolean,
    failure: String,
    setCloseDialog: () -> Unit,
    closeMov: () -> Unit,
    flagCloseMov: Boolean,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
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
        TitleDesign(text = stringResource(id = R.string.text_title_detalhe))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = "DATA/HORA: $dthr",
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
                    text = "VEICULO: $veiculo",
                    setActionItem = onNavVeiculo,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = "PLACA: $placa",
                    setActionItem = onNavPlaca,
                    id = 2
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
                    text = "MOTORISTA: $motorista",
                    setActionItem = onNavCpf
                )
            }
            item {
                ItemListDesign(
                    text = "PASSAGEIRO(S): $passageiro",
                    setActionItem = onNavPassagList
                )
            }
            item {
                ItemListDesign(
                    text = "DESTINO: ${if(destino.isNullOrEmpty()) "" else destino}" ,
                    setActionItem = onNavDestino
                )
            }
            item {
                ItemListDesign(
                    text = "OBSERVAÇÃO: ${if(observ.isNullOrEmpty()) "" else observ}",
                    setActionItem = onNavObserv
                )
            }
        }
        Button(
            onClick = { setDialogCheck(true) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_button_close_mov))
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Button(
            onClick = onNavMovEquipEditList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if (flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavMovEquipEditList() }
            )
        }

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_mov),
                setCloseDialog = { setDialogCheck(false)  },
                setActionButtonOK = { closeMov() }
            )
        }

        if(flagCloseMov){
            onNavMovEquipEditList()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalheVisitTercPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheVisitTercContent(
                dthr = "29/08/2024 16:14",
                tipoMov = "ENTRADA",
                veiculo = "100",
                placa = "ABC-1234",
                tipoVisitTerc = "VISITANTE",
                motorista = "123.456.789-00 - ANDERSON DA SILVA DELGADO",
                passageiro = "123.456.789-00 - JOSE DONIZETE;",
                destino = null,
                observ = "TESTE OBSERVAÇÃO",
                setCloseDialog = {},
                closeMov = {},
                flagCloseMov = false,
                flagDialogCheck = false,
                flagDialog = false,
                failure = "",
                setDialogCheck = {},
                onNavMovEquipEditList = {},
                onNavVeiculo = {},
                onNavPlaca = {},
                onNavCpf = {},
                onNavPassagList = {},
                onNavDestino = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}