package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_PROPRIO_EQUIP_SEG

@Entity(tableName = TB_MOV_EQUIP_PROPRIO_EQUIP_SEG)
data class MovEquipProprioEquipSegRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipProprioEquipSeg: Int? = null,
    var idMovEquipProprio: Int,
    var idEquip: Int,
)

fun MovEquipProprioEquipSeg.entityToRoomModel(id: Int): MovEquipProprioEquipSegRoomModel{
    return with(this){
        MovEquipProprioEquipSegRoomModel(
            idMovEquipProprio = id,
            idEquip = this.idEquip!!,
        )
    }
}

fun MovEquipProprioEquipSegRoomModel.modelRoomToEntity(): MovEquipProprioEquipSeg {
    return with(this){
        MovEquipProprioEquipSeg(
            idMovEquipProprioEquipSeg = this.idMovEquipProprioEquipSeg,
            idMovEquipProprio = this.idMovEquipProprio,
            idEquip = this.idEquip,
        )
    }
}