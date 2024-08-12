package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.utils.TB_VISITANTE
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import kotlinx.serialization.Serializable

@Entity(tableName = TB_VISITANTE)
data class VisitanteRoomModel(
    @PrimaryKey
    val idVisitante: Long,
    val cpfVisitante: String,
    val nomeVisitante: String,
    val empresaVisitante: String,
)

fun VisitanteRoomModel.toVisitante(): Visitante {
    return with(this){
        Visitante(
            idVisitante = this.idVisitante,
            cpfVisitante = this.cpfVisitante,
            nomeVisitante = this.nomeVisitante,
            empresaVisitante = this.empresaVisitante,
        )
    }
}

fun Visitante.toVisitanteModel(): VisitanteRoomModel{
    return with(this){
        VisitanteRoomModel(
            idVisitante = this.idVisitante,
            cpfVisitante = this.cpfVisitante,
            nomeVisitante = this.nomeVisitante,
            empresaVisitante = this.empresaVisitante,
        )
    }
}