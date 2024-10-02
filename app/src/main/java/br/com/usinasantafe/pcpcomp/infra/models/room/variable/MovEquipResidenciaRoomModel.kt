package br.com.usinasantafe.pcpcomp.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_RESIDENCIA
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

@Entity(tableName = TB_MOV_EQUIP_RESIDENCIA)
data class MovEquipResidenciaRoomModel(
    @PrimaryKey(autoGenerate = true)
    var idMovEquipResidencia: Int? = null,
    var nroMatricVigiaMovEquipResidencia: Int,
    var idLocalMovEquipResidencia: Int,
    var tipoMovEquipResidencia: TypeMov,
    var dthrMovEquipResidencia: Long,
    var motoristaMovEquipResidencia: String,
    var veiculoMovEquipResidencia: String,
    var placaMovEquipResidencia: String,
    var observMovEquipResidencia: String?,
    var statusMovEquipResidencia: StatusData,
    var statusSendMovEquipResidencia: StatusSend,
    var statusMovEquipForeigResidencia: StatusForeigner,
)

fun MovEquipResidenciaRoomModel.roomModelToEntity(): MovEquipResidencia {
    return with(this){
        MovEquipResidencia(
            idMovEquipResidencia = this.idMovEquipResidencia,
            nroMatricVigiaMovEquipResidencia = this.nroMatricVigiaMovEquipResidencia,
            idLocalMovEquipResidencia = this.idLocalMovEquipResidencia,
            dthrMovEquipResidencia = Date(this.dthrMovEquipResidencia),
            tipoMovEquipResidencia = this.tipoMovEquipResidencia,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia,
            placaMovEquipResidencia = this.placaMovEquipResidencia,
            observMovEquipResidencia = this.observMovEquipResidencia,
            statusMovEquipResidencia = this.statusMovEquipResidencia,
            statusSendMovEquipResidencia = this.statusSendMovEquipResidencia,
            statusMovEquipForeigResidencia = this.statusMovEquipForeigResidencia,
        )
    }
}

fun MovEquipResidencia.entityToRoomModel(matricVigia: Int, idLocal: Int): MovEquipResidenciaRoomModel{
    return with(this){
        MovEquipResidenciaRoomModel(
            idMovEquipResidencia = this.idMovEquipResidencia,
            nroMatricVigiaMovEquipResidencia = matricVigia,
            idLocalMovEquipResidencia = idLocal,
            dthrMovEquipResidencia = this.dthrMovEquipResidencia.time,
            tipoMovEquipResidencia = this.tipoMovEquipResidencia!!,
            motoristaMovEquipResidencia = this.motoristaMovEquipResidencia!!,
            veiculoMovEquipResidencia = this.veiculoMovEquipResidencia!!,
            placaMovEquipResidencia = this.placaMovEquipResidencia!!,
            observMovEquipResidencia = this.observMovEquipResidencia,
            statusMovEquipResidencia = this.statusMovEquipResidencia,
            statusSendMovEquipResidencia = this.statusSendMovEquipResidencia,
            statusMovEquipForeigResidencia = this.statusMovEquipForeigResidencia,
        )
    }
}
