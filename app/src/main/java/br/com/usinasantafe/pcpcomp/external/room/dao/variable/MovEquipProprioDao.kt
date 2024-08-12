package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_PROPRIO

@Dao
interface MovEquipProprioDao {

    @Insert
    suspend fun insert(movEquipProprioRoomModel: MovEquipProprioRoomModel)

    @Update
    suspend fun update(movEquipProprioRoomModel: MovEquipProprioRoomModel)

    @Delete
    suspend fun delete(movEquipProprioRoomModel: MovEquipProprioRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO WHERE statusMovEquipProprio = :status")
    suspend fun listStatus(status: StatusData): List<MovEquipProprioRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO WHERE statusSendMovEquipProprio = :statusEnvio")
    suspend fun listStatusSend(statusEnvio: StatusSend): List<MovEquipProprioRoomModel>

    @Query("SELECT MAX(idMovEquipProprio) FROM $TB_MOV_EQUIP_PROPRIO WHERE statusMovEquipProprio = :status")
    suspend fun lastIdStatus(status: StatusData): Long

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO WHERE idMovEquipProprio = :idMov")
    suspend fun getId(idMov: Long): MovEquipProprioRoomModel

}