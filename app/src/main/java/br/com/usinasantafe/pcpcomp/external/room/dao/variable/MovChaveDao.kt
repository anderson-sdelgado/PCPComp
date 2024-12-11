package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TB_MOV_CHAVE

@Dao
interface MovChaveDao {

    @Insert
    suspend fun insert(movChaveRoomModel: MovChaveRoomModel): Long

    @Query("SELECT * FROM $TB_MOV_CHAVE WHERE statusForeignerMovChave = :statusForeigner")
    suspend fun listStatusForeigner(statusForeigner: StatusForeigner): List<MovChaveRoomModel>

}