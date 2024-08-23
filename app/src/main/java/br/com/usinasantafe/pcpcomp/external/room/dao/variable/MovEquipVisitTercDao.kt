package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_VISIT_TERC

@Dao
interface MovEquipVisitTercDao {

    @Insert
    suspend fun insert(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel)

    @Update
    suspend fun update(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel)

    @Delete
    suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusMovEquipVisitTerc = :status")
    suspend fun listStatus(status: StatusData): List<MovEquipVisitTercRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusSendMovEquipVisitTerc = :statusEnvio")
    suspend fun listStatusEnvio(statusEnvio: StatusSend): List<MovEquipVisitTercRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusMovEquipForeigVisitTerc = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovEquipVisitTercRoomModel>

    @Query("SELECT MAX(idMovEquipVisitTerc) FROM $TB_MOV_EQUIP_VISIT_TERC WHERE statusMovEquipVisitTerc = :status")
    suspend fun lastIdStatus(status: StatusData): Int

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC WHERE idMovEquipVisitTerc = :idMov")
    suspend fun getId(idMov: Int): MovEquipVisitTercRoomModel

}