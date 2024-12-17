package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_CHAVE
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_EQUIP_RESIDENCIA

@Dao
interface MovChaveDao {

    @Insert
    suspend fun insert(movChaveRoomModel: MovChaveRoomModel): Long

    @Update
    suspend fun update(movChaveRoomModel: MovChaveRoomModel)

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE idMovChave = :id")
    suspend fun get(id: Int): MovChaveRoomModel

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE statusMovChave = :status")
    suspend fun listStatusData(status: StatusData): List<MovChaveRoomModel>

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE statusForeignerMovChave = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovChaveRoomModel>

}