package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_CHAVE
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

@Entity(tableName = TB_MOV_CHAVE)
data class MovChaveRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovChave: Int? = null,
    var matricVigiaMovChave: Int,
    var idLocalMovChave: Int,
    var tipoMovChave: TypeMovKey,
    var dthrMovChave: Long,
    var idChaveMovChave: Int,
    var matricColabMovChave: Int,
    var observMovChave: String?,
    var statusMovChave: StatusData,
    var statusSendMovChave: StatusSend,
    var statusForeignerMovChave: StatusForeigner
)

fun MovChaveRoomModel.roomModelToEntity(): MovChave {
    return with(this){
        MovChave(
            idMovChave = this.idMovChave,
            matricVigiaMovChave = this.matricVigiaMovChave,
            idLocalMovChave = this.idLocalMovChave,
            idChaveMovChave = this.idChaveMovChave,
            dthrMovChave = Date(this.dthrMovChave),
            tipoMovChave = this.tipoMovChave,
            matricColabMovChave = this.matricColabMovChave,
            observMovChave = this.observMovChave,
            statusMovChave = this.statusMovChave,
            statusSendMovChave = this.statusSendMovChave,
            statusForeignerMovChave = this.statusForeignerMovChave,
        )
    }
}

fun MovChave.entityToRoomModel(
    matricVigia: Int,
    idLocal: Int
): MovChaveRoomModel {
    return with(this){
        MovChaveRoomModel(
            idMovChave = this.idMovChave,
            matricVigiaMovChave = matricVigia,
            idLocalMovChave = idLocal,
            idChaveMovChave = this.idChaveMovChave!!,
            dthrMovChave = this.dthrMovChave.time,
            tipoMovChave = this.tipoMovChave!!,
            matricColabMovChave = this.matricColabMovChave!!,
            observMovChave = this.observMovChave,
            statusMovChave = this.statusMovChave,
            statusSendMovChave = this.statusSendMovChave,
            statusForeignerMovChave = this.statusForeignerMovChave,
        )
    }
}
