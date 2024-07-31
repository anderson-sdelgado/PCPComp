package br.com.usinasantafe.pcpcomp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_LOCAL

@Dao
interface LocalDao {

    @Insert
    fun insertAll(list: List<LocalRoomModel>)

    @Query("DELETE FROM $TB_LOCAL")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_LOCAL ORDER BY descrLocal ASC")
    suspend fun listAll(): List<LocalRoomModel>

    @Query("SELECT * FROM $TB_LOCAL WHERE idLocal = :id")
    suspend fun getLocalId(id: Long): LocalRoomModel

}