package br.com.usinasantafe.pcpcomp.presenter.visitterc.model

data class MovEquipVisitTercModel(
    val id: Int,
    val dthr: String,
    val tipo: String? = null,
    val motorista: String,
    val veiculo: String,
    val placa: String,
)
