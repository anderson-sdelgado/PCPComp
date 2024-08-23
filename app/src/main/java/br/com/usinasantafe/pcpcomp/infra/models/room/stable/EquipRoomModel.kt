package br.com.usinasantafe.pcpcomp.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.utils.TB_EQUIP
import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import kotlinx.serialization.Serializable

@Entity(tableName = TB_EQUIP)
data class EquipRoomModel (
    @PrimaryKey
    val idEquip: Int,
    val nroEquip: Long,
)

fun EquipRoomModel.toEquip(): Equip {
    return with(this){
        Equip(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
        )
    }
}

fun Equip.toEquipModel(): EquipRoomModel{
    return with(this){
        EquipRoomModel(
            idEquip = this.idEquip,
            nroEquip = this.nroEquip,
        )
    }
}