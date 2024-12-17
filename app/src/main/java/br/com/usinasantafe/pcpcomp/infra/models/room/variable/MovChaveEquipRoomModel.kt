package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_CHAVE_EQUIP
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

@Entity(tableName = TB_MOV_CHAVE_EQUIP)
data class MovChaveEquipRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovChaveEquip: Int? = null,
    var matricVigiaMovChaveEquip: Int,
    var idLocalMovChaveEquip: Int,
    var dthrMovChaveEquip: Long,
    var tipoMovChaveEquip: TypeMovKey,
    var idEquipMovChaveEquip: Int,
    var matricColabMovChaveEquip: Int,
    var observMovChaveEquip: String?,
    var statusMovChaveEquip: StatusData,
    var statusSendMovChaveEquip: StatusSend,
    var statusForeignerMovChaveEquip: StatusForeigner,
)

fun MovChaveEquipRoomModel.roomModelToEntity(): MovChaveEquip {
    return with(this){
        MovChaveEquip(
            idMovChaveEquip = this.idMovChaveEquip,
            matricVigiaMovChaveEquip = this.matricVigiaMovChaveEquip,
            idLocalMovChaveEquip = this.idLocalMovChaveEquip,
            idEquipMovChaveEquip = this.idEquipMovChaveEquip,
            dthrMovChaveEquip = Date(this.dthrMovChaveEquip),
            tipoMovChaveEquip = this.tipoMovChaveEquip,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip,
            observMovChaveEquip = this.observMovChaveEquip,
            statusMovChaveEquip = this.statusMovChaveEquip,
            statusSendMovChaveEquip = this.statusSendMovChaveEquip,
            statusForeignerMovChaveEquip = this.statusForeignerMovChaveEquip,
        )
    }
}

fun MovChaveEquip.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int
): MovChaveEquipRoomModel {
    return with(this){
        MovChaveEquipRoomModel(
            idMovChaveEquip = this.idMovChaveEquip,
            matricVigiaMovChaveEquip = matricVigia,
            idLocalMovChaveEquip = idLocal,
            idEquipMovChaveEquip = this.idEquipMovChaveEquip!!,
            dthrMovChaveEquip = this.dthrMovChaveEquip.time,
            tipoMovChaveEquip = this.tipoMovChaveEquip!!,
            matricColabMovChaveEquip = this.matricColabMovChaveEquip!!,
            observMovChaveEquip = this.observMovChaveEquip,
            statusMovChaveEquip = this.statusMovChaveEquip,
            statusSendMovChaveEquip = this.statusSendMovChaveEquip,
            statusForeignerMovChaveEquip = this.statusForeignerMovChaveEquip,
        )
    }
}