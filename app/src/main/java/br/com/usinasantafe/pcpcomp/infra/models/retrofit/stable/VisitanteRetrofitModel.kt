package br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable

import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante

data class VisitanteRetrofitModel(
    val idVisitante: Int,
    val cpfVisitante: String,
    val nomeVisitante: String,
    val empresaVisitante: String,
)

fun VisitanteRetrofitModel.retrofitModelToEntity(): Visitante {
    return with(this){
        Visitante(
            idVisitante = this.idVisitante,
            cpfVisitante = this.cpfVisitante,
            nomeVisitante = this.nomeVisitante,
            empresaVisitante = this.empresaVisitante,
        )
    }
}