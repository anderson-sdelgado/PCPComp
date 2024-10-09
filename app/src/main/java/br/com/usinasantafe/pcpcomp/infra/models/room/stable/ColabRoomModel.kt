package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel(
    @PrimaryKey
    val matricColab: Int,
    val nomeColab: String,
)

fun Colab.entityToRoomModel(): ColabRoomModel {
    return with(this) {
        ColabRoomModel(
            matricColab = this.matricColab,
            nomeColab = this.nomeColab,
        )
    }
}