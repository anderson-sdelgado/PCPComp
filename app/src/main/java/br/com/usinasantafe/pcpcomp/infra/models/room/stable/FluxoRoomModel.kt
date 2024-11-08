package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.utils.TB_FLUXO

@Entity(tableName = TB_FLUXO)
data class FluxoRoomModel(
    @PrimaryKey
    val idFluxo: Int,
    val descrFluxo: String,
)

fun Fluxo.entityToRoomModel(): FluxoRoomModel {
    return with(this) {
        FluxoRoomModel(
            idFluxo = this.idFluxo,
            descrFluxo = this.descrFluxo,
        )
    }
}