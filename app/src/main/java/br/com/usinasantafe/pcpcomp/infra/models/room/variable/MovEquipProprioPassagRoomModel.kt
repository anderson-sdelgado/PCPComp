package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_PROPRIO_PASSAG

@Entity(tableName = TB_MOV_EQUIP_PROPRIO_PASSAG)
data class MovEquipProprioPassagRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipProprioPassag: Int? = null,
    var idMovEquipProprio: Int,
    var matricColab: Int,
)

fun MovEquipProprioPassagRoomModel.modelRoomToEntity(): MovEquipProprioPassag {
    return with(this){
        MovEquipProprioPassag(
            idMovEquipProprioPassag = this.idMovEquipProprioPassag,
            idMovEquipProprio = this.idMovEquipProprio,
            matricColab = this.matricColab,
        )
    }
}
