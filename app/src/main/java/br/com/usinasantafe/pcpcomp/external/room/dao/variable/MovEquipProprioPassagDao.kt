package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_PROPRIO_PASSAG

@Dao
interface MovEquipProprioPassagDao {

    @Insert
    suspend fun insert(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel)

    @Insert
    suspend fun insertAll(list: List<MovEquipProprioPassagRoomModel>)

    @Delete
    suspend fun delete(movEquipProprioPassagRoomModel: MovEquipProprioPassagRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_PASSAG WHERE idMovEquipProprio = :idMov")
    suspend fun list(idMov: Int): List<MovEquipProprioPassagRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_PASSAG WHERE idMovEquipProprio = :idMov AND matricColab = :matricColab")
    suspend fun get(idMov: Int, matricColab: Int): MovEquipProprioPassagRoomModel
}