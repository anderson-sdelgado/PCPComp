package br.com.usinasantafe.pcpcomp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalTrabRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_LOCAL_TRAB

@Dao
interface LocalTrabDao {

    @Insert
    suspend fun insertAll(list: List<LocalTrabRoomModel>)

    @Query("DELETE FROM $TB_LOCAL_TRAB")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_LOCAL_TRAB WHERE idLocalTrab = :id")
    suspend fun get(id: Int): LocalTrabRoomModel

    @Query("SELECT * FROM $TB_LOCAL_TRAB")
    suspend fun listAll(): List<LocalTrabRoomModel>

}