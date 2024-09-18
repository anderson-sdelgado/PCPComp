package br.com.usinasantafe.pcpcomp.presenter.residencia.model

data class MovEquipResidenciaModel(
    val id: Int,
    val dthr: String,
    val tipo: String? = null,
    val veiculo: String,
    val placa: String,
    val motorista: String,
)
