package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.utils.TB_FLUXO
import br.com.usinasantafe.pcpcomp.utils.TB_R_LOCAL_FLUXO


@Entity(tableName = TB_R_LOCAL_FLUXO)
data class RLocalFluxoRoomModel(
    @PrimaryKey
    val idRLocalFluxo: Int,
    val idLocal: Int,
    val idFluxo: Int,
)

fun RLocalFluxo.entityToRoomModel(): RLocalFluxoRoomModel {
    return with(this) {
        RLocalFluxoRoomModel(
            idRLocalFluxo = this.idRLocalFluxo,
            idFluxo = this.idFluxo,
            idLocal = this.idLocal,
        )
    }
}