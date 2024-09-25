package br.com.usinasantafe.pcpcomp.presenter.visitterc.model

data class MovEquipVisitTercModel(
    val id: Int,
    val dthr: String,
    val tipoMov: String? = null,
    val tipoVisitTerc: String,
    val motorista: String,
    val veiculo: String,
    val placa: String,
)
