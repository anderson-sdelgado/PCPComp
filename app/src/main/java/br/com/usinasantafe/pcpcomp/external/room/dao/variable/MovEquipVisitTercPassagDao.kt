package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_VISIT_TERC_PASSAG

@Dao
interface MovEquipVisitTercPassagDao {

    @Insert
    suspend fun insert(movEquipVisitTercPassagRoomModel: MovEquipVisitTercPassagRoomModel)

    @Insert
    suspend fun insertAll(list: List<MovEquipVisitTercPassagRoomModel>)

    @Delete
    suspend fun delete(movEquipVisitTercPassagRoomModel: MovEquipVisitTercPassagRoomModel)

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC_PASSAG WHERE idMovEquipVisitTerc = :idMov")
    suspend fun list(idMov: Int): List<MovEquipVisitTercPassagRoomModel>

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC_PASSAG WHERE idMovEquipVisitTerc = :idMov AND idVisitTerc = :idVisitTerc")
    suspend fun get(idMov: Int, idVisitTerc: Int): MovEquipVisitTercPassagRoomModel

    @Query("SELECT * FROM $TB_MOV_EQUIP_VISIT_TERC_PASSAG WHERE idMovEquipVisitTerc = :idMov")
    suspend fun get(idMov: Int): List<MovEquipVisitTercPassagRoomModel>
}