package br.com.usinasantafe.pcpcomp.presenter.proprio.detalhe

data class DetalheProprioModel(
    val dthr: String,
    val tipoMov: String,
    val veiculo: String,
    val motorista: String,
    val passageiro: String,
    val destino: String,
    val veiculoSec: String,
    val notaFiscal: String?,
    val observ: String?,
)
