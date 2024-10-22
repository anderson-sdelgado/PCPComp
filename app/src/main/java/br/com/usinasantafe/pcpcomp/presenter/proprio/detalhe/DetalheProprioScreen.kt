package br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe

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
                setCloseDialog = viewModel::setCloseDialog,
                closeMov = viewModel::closeMov,
                flagCloseMov = uiState.flagCloseMov,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                flagDialog = uiState.flagDialog,
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
    notaFiscal: String?,
    observ: String?,
    setCloseDialog: () -> Unit,
    closeMov: () -> Unit,
    flagCloseMov: Boolean,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    flagDialog: Boolean,
    failure: String,
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
                    setActionItem = onNavNroEquip,
                    id = 1
                )
            }
            item {
                ItemListDesign(
                    text = "VEICULO SECUNDÁRIO: $veiculoSec",
                    setActionItem = onNavEquipSegList,
                    id = 2
                )
            }
            item {
                ItemListDesign(
                    text = "MOTORISTA: $motorista",
                    setActionItem = onNavMatricColab
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
                    text = "DESTINO: $destino",
                    setActionItem = onNavDestino
                )
            }
            item {
                ItemListDesign(
                    text = "NOTA FISCAL: ${if(notaFiscal.isNullOrEmpty()) "" else notaFiscal}",
                    setActionItem = onNavNotaFiscal
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

        if(flagDialogCheck){
            AlertDialogCheckDesign(
                text = stringResource(id = R.string.text_question_close_all_mov),
                setCloseDialog = { setDialogCheck(false)  },
                setActionButtonOK = { closeMov() }
            )
        }

        if(flagCloseMov){
            onNavMovProprioList()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetalheMovProprioPagePreview() {
    PCPCompTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DetalheMovProprioContent(
                dthr = "29/08/2024 16:14",
                tipoMov = "ENTRADA",
                veiculo = "100",
                veiculoSec = "101 - 200",
                motorista = "19759 - ANDERSON DA SILVA DELGADO",
                passageiro = "19035 - JOSE DONIZETE; 18017 - RONALDO;",
                destino = "TESTE DESTINO",
                notaFiscal = "NOTA FISCAL:",
                observ = "TESTE OBSERVAÇÃO",
                flagDialog = false,
                failure = "",
                setCloseDialog = {},
                flagDialogCheck = false,
                setDialogCheck = {},
                closeMov = {},
                flagCloseMov = false,
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