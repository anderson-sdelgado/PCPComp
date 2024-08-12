package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_VISIT_TERC
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import java.util.Date

@Entity(tableName = TB_MOV_EQUIP_VISIT_TERC)
data class MovEquipVisitTercRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipVisitTerc: Long? = null,
    var nroMatricVigiaMovEquipVisitTerc: Long,
    var idLocalMovEquipVisitTerc: Long,
    var tipoMovEquipVisitTerc: TypeMov,
    var idVisitTercMovEquipVisitTerc: Long,
    var tipoVisitTercMovEquipVisitTerc: TypeVisitTerc,
    var dthrMovEquipVisitTerc: Long,
    var veiculoMovEquipVisitTerc: String,
    var placaMovEquipVisitTerc: String,
    var destinoMovEquipVisitTerc: String?,
    var observMovEquipVisitTerc: String?,
    var statusMovEquipVisitTerc: StatusData,
    var statusSendMovEquipVisitTerc: StatusSend,
    var statusMovEquipForeigVisitTerc: StatusForeigner,
)

fun MovEquipVisitTercRoomModel.modelRoomToMovEquipVisitTerc(): MovEquipVisitTerc {
    return with(this){
        MovEquipVisitTerc(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            nroMatricVigiaMovEquipVisitTerc = this.nroMatricVigiaMovEquipVisitTerc,
            idLocalMovEquipVisitTerc = this.idLocalMovEquipVisitTerc,
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc,
            dthrMovEquipVisitTerc = Date(this.dthrMovEquipVisitTerc),
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            statusMovEquipVisitTerc = this.statusMovEquipVisitTerc,
            statusSendMovEquipVisitTerc = this.statusSendMovEquipVisitTerc,
            statusMovEquipForeigVisitTerc = this.statusMovEquipForeigVisitTerc,
        )
    }
}

fun MovEquipVisitTerc.entityToMovEquipVisitTercRoomModel(matricVigia: Long, idLocal: Long): MovEquipVisitTercRoomModel{
    return with(this){
        MovEquipVisitTercRoomModel(
            idMovEquipVisitTerc = this.idMovEquipVisitTerc,
            nroMatricVigiaMovEquipVisitTerc = matricVigia,
            idLocalMovEquipVisitTerc = idLocal,
            tipoMovEquipVisitTerc = this.tipoMovEquipVisitTerc!!,
            idVisitTercMovEquipVisitTerc = this.idVisitTercMovEquipVisitTerc!!,
            tipoVisitTercMovEquipVisitTerc = this.tipoVisitTercMovEquipVisitTerc!!,
            dthrMovEquipVisitTerc = this.dthrMovEquipVisitTerc.time,
            veiculoMovEquipVisitTerc = this.veiculoMovEquipVisitTerc!!,
            placaMovEquipVisitTerc = this.placaMovEquipVisitTerc!!,
            destinoMovEquipVisitTerc = this.destinoMovEquipVisitTerc,
            observMovEquipVisitTerc = this.observMovEquipVisitTerc,
            statusMovEquipVisitTerc = this.statusMovEquipVisitTerc,
            statusSendMovEquipVisitTerc = this.statusSendMovEquipVisitTerc,
            statusMovEquipForeigVisitTerc = this.statusMovEquipForeigVisitTerc,
        )
    }
}