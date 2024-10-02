package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_VISIT_TERC_PASSAG

@Entity(tableName = TB_MOV_EQUIP_VISIT_TERC_PASSAG)
data class MovEquipVisitTercPassagRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipVisitTercPassag: Int? = null,
    var idMovEquipVisitTerc: Int,
    var idVisitTerc: Int,
)


fun MovEquipVisitTercPassagRoomModel.modelRoomToEntity(): MovEquipVisitTercPassag {
    return with(this){
        MovEquipVisitTercPassag(
            idMovEquipVisitTercPassag = this.idMovEquipVisitTercPassag,
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            idVisitTerc = this.idVisitTerc,
        )
    }
}
