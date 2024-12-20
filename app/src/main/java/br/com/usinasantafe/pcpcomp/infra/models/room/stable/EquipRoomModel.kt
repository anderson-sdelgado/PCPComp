package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.utils.TB_EQUIP
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val idEquip: Int,
    val nroEquip: Long,
    val descrEquip: String
)

fun Equip.entityToRoomModel(): EquipRoomModel{
    return with(this){
        EquipRoomModel(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            descrEquip = this.descrEquip
        )
    }
}

fun EquipRoomModel.roomModelToEntity(): Equip{
    return with(this){
        Equip(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
            descrEquip = this.descrEquip
        )
    }
}