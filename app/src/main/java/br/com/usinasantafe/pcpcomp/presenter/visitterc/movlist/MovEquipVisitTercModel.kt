package br.com.usinasantafe.pcpcomp.presenter.visitterc.movlist

data class MovEquipVisitTercModel(
    val id: Int,
    val dthr: String,
    val motorista: String,
    val veiculo: String,
    val placa: String,
    val tipo: String? = null,
)
