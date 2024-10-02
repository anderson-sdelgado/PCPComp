package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_PROPRIO
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date


@Entity(tableName = TB_MOV_EQUIP_PROPRIO)
data class MovEquipProprioRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipProprio: Int? = null,
    var matricVigiaMovEquipProprio: Int,
    var idLocalMovEquipProprio: Int,
    var tipoMovEquipProprio: TypeMov,
    var dthrMovEquipProprio: Long,
    var idEquipMovEquipProprio: Int,
    var matricColabMovEquipProprio: Int,
    var destinoMovEquipProprio: String,
    var notaFiscalMovEquipProprio: Int?,
    var observMovEquipProprio: String?,
    var statusMovEquipProprio: StatusData,
    var statusSendMovEquipProprio: StatusSend,
)

fun MovEquipProprioRoomModel.roomModelToEntity(): MovEquipProprio {
    return with(this){
        MovEquipProprio(
            idMovEquipProprio = this.idMovEquipProprio,
            matricVigiaMovEquipProprio = this.matricVigiaMovEquipProprio,
            idLocalMovEquipProprio = this.idLocalMovEquipProprio,
            tipoMovEquipProprio = this.tipoMovEquipProprio,
            dthrMovEquipProprio = Date(this.dthrMovEquipProprio),
            idEquipMovEquipProprio = this.idEquipMovEquipProprio,
            matricColabMovEquipProprio = this.matricColabMovEquipProprio,
            destinoMovEquipProprio = this.destinoMovEquipProprio,
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            statusMovEquipProprio = this.statusMovEquipProprio,
            statusSendMovEquipProprio = this.statusSendMovEquipProprio,
        )
    }
}

fun MovEquipProprio.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int
): MovEquipProprioRoomModel{
    return with(this){
        MovEquipProprioRoomModel(
            idMovEquipProprio = idMovEquipProprio,
            matricVigiaMovEquipProprio = matricVigia,
            idLocalMovEquipProprio = idLocal,
            tipoMovEquipProprio = this.tipoMovEquipProprio!!,
            dthrMovEquipProprio = this.dthrMovEquipProprio.time,
            idEquipMovEquipProprio = this.idEquipMovEquipProprio!!,
            matricColabMovEquipProprio = this.matricColabMovEquipProprio!!,
            destinoMovEquipProprio = this.destinoMovEquipProprio!!,
            notaFiscalMovEquipProprio = this.notaFiscalMovEquipProprio,
            observMovEquipProprio = this.observMovEquipProprio,
            statusMovEquipProprio = this.statusMovEquipProprio,
            statusSendMovEquipProprio = this.statusSendMovEquipProprio,
        )
    }
}