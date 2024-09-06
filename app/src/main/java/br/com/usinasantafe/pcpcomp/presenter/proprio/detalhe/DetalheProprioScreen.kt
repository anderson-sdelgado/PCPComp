package br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe

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
fun DetalheMovProprioScreen(
    viewModel: DetalheProprioViewModel,
    onNavMovProprioList: () -> Unit,
    onNavNroEquip: () -> Unit,
    onNavEquipSegList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavNotaFiscal: () -> Unit,
    onNavObserv: () -> Unit
) {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DetalheMovProprioContent(
                dthr = uiState.dthr,
                tipoMov = uiState.tipoMov,
                veiculo = uiState.veiculo,
                motorista = uiState.motorista,
                passageiro = uiState.passageiro,
                destino = uiState.destino,
                veiculoSec = uiState.veiculoSec,
                notaFiscal = uiState.notaFiscal,
                observ = uiState.observ,
                flagDialog = uiState.flagDialog,
                setCloseDialog = viewModel::setCloseDialog,
                failure = uiState.failure,
                onNavMovProprioList = onNavMovProprioList,
                onNavNroEquip = onNavNroEquip,
                onNavEquipSegList = onNavEquipSegList,
                onNavMatricColab = onNavMatricColab,
                onNavPassagList = onNavPassagList,
                onNavDestino = onNavDestino,
                onNavNotaFiscal = onNavNotaFiscal,
                onNavObserv = onNavObserv,
                modifier = Modifier.padding(innerPadding)
            )
            viewModel.recoverDetalhe()
        }
    }
}

@Composable
fun DetalheMovProprioContent(
    dthr: String,
    tipoMov: String,
    veiculo: String,
    motorista: String,
    passageiro: String,
    destino: String,
    veiculoSec: String,
    notaFiscal: String,
    observ: String,
    flagDialog: Boolean,
    failure: String,
    setCloseDialog: () -> Unit,
    onNavMovProprioList: () -> Unit,
    onNavNroEquip: () -> Unit,
    onNavEquipSegList: () -> Unit,
    onNavMatricColab: () -> Unit,
    onNavPassagList: () -> Unit,
    onNavDestino: () -> Unit,
    onNavNotaFiscal: () -> Unit,
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
                    setActionItem = onNavNroEquip,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = veiculoSec,
                    setActionItem = onNavEquipSegList,
                    id = 2
                )
            }
            item {
                ItemListDesign(
                    text = motorista,
                    setActionItem = onNavMatricColab
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
                    text = notaFiscal,
                    setActionItem = onNavNotaFiscal
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
            onClick = onNavMovProprioList,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(text = stringResource(id = R.string.text_pattern_return))
        }
        BackHandler {}

        if(flagDialog) {
            AlertDialogSimpleDesign(
                text = stringResource(id = R.string.text_failure, failure),
                setCloseDialog = setCloseDialog,
                setActionButtonOK = { onNavMovProprioList() }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalheMovProprioPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheMovProprioContent(
                dthr = "DATA/HORA: 29/08/2024 16:14",
                tipoMov = "ENTRADA",
                veiculo = "VEÍCULO: 100",
                veiculoSec = "VEÍCULO SECUNDÁRIO: 101 - 200",
                motorista = "MOTORISTA: 19759 - ANDERSON DA SILVA DELGADO",
                passageiro = "PASSAGEIRO(S): 19035 - JOSE DONIZETE; 18017 - RONALDO;",
                destino = "DESTINO: TESTE DESTINO",
                notaFiscal = "NOTA FISCAL:",
                observ = "OBSERVAÇÃO: TESTE OBSERVAÇÃO",
                flagDialog = false,
                failure = "",
                setCloseDialog = {},
                onNavMovProprioList = {},
                onNavNroEquip = {},
                onNavEquipSegList = {},
                onNavMatricColab = {},
                onNavPassagList = {},
                onNavDestino = {},
                onNavNotaFiscal = {},
                onNavObserv = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}