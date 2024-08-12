package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_RESIDENCIA

@Dao
interface MovEquipResidenciaDao {

    @Insert
    suspend fun insert(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Update
    suspend fun update(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Delete
    suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status")
    suspend fun listStatus(status: StatusData): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipForeigResidencia = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusSendMovEquipResidencia = :statusEnvio")
    suspend fun listStatusEnvio(statusEnvio: StatusSend): List<MovEquipResidenciaRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status AND statusSendMovEquipResidencia = :statusEnvio")
    suspend fun listStatusAndStatusEnvio(status: StatusData, statusEnvio: StatusSend): List<MovEquipResidenciaRoomModel>

    @Query("SELECT MAX(idMovEquipResidencia) FROM $TB_MOV_EQUIP_RESIDENCIA WHERE statusMovEquipResidencia = :status")
    suspend fun lastIdStatus(status: StatusData): Long

    @Query("SELECT * FROM $TB_MOV_EQUIP_RESIDENCIA WHERE idMovEquipResidencia = :idMov")
    suspend fun getId(idMov: Long): MovEquipResidenciaRoomModel

}