package br.com.usinasantafe.pcpcomp.presenter.visitterc.detalhe

data class DetalheVisitTercModel(
    val dthr: String,
    val tipoMov: String,
    val veiculo: String,
    val placa: String,
    val tipoVisitTerc: String,
    val motorista: String,
    val passageiro: String,
    val destino: String?,
    val observ: String?,
)
