package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_PROPRIO_EQUIP_SEG

@Dao
interface MovEquipProprioEquipSegDao {

    @Insert
    suspend fun insert(movEquipProprioEquipSegRoomModel: MovEquipProprioEquipSegRoomModel)

    @Insert
    suspend fun insertAll(list: List<MovEquipProprioEquipSegRoomModel>)

    @Delete
    suspend fun delete(movEquipProprioEquipSegRoomModel: MovEquipProprioEquipSegRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_EQUIP_SEG WHERE idMovEquipProprio = :idMov")
    suspend fun list(idMov: Int): List<MovEquipProprioEquipSegRoomModel>


    @Query("SELECT * FROM $TB_MOV_EQUIP_PROPRIO_EQUIP_SEG WHERE idMovEquipProprio = :idMov AND idEquip = :idEquip")
    suspend fun get(idMov: Int, idEquip: Int): MovEquipProprioEquipSegRoomModel
}