package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_CHAVE_EQUIP

@Dao
interface MovChaveEquipDao {

    @Insert
    suspend fun insert(movChaveEquipRoomModel: MovChaveEquipRoomModel): Long

    @Update
    suspend fun update(movChaveEquipRoomModel: MovChaveEquipRoomModel)

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE idMovChaveEquip = :id")
    suspend fun get(id: Int): MovChaveEquipRoomModel

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusMovChaveEquip = :status")
    suspend fun listStatusData(status: StatusData): List<MovChaveEquipRoomModel>

    @Query("SELECT * FROM $TB_MOV_CHAVE_EQUIP WHERE statusForeignerMovChaveEquip = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovChaveEquipRoomModel>

}