package br.com.usinasantafe.pcpcomp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_CHAVE

@Dao
interface ChaveDao {

    @Insert
    suspend fun insertAll(list: List<ChaveRoomModel>)

    @Query("DELETE FROM $TB_CHAVE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_CHAVE WHERE idChave = :id")
    suspend fun get(id: Int): ChaveRoomModel

    @Query("SELECT * FROM $TB_CHAVE ORDER BY descrChave")
    suspend fun listAll(): List<ChaveRoomModel>

}