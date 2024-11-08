package br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable

import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro

data class TerceiroRetrofitModel (
    val idTerceiro: Int,
    val idBDTerceiro: Int,
    val cpfTerceiro: String,
    val nomeTerceiro: String,
    val empresaTerceiro: String,
)

fun TerceiroRetrofitModel.retrofitModelToEntity(): Terceiro {
    return with(this){
        Terceiro(
            idTerceiro = this.idTerceiro,
            idBDTerceiro = this.idBDTerceiro,
            cpfTerceiro = this.cpfTerceiro,
            nomeTerceiro = this.nomeTerceiro,
            empresaTerceiro = this.empresaTerceiro,
        )
    }
}