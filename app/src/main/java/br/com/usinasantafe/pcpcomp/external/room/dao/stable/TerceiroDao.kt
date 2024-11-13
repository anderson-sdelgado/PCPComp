package br.com.usinasantafe.pcpcomp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_TERCEIRO

@Dao
interface TerceiroDao {

    @Insert
    fun insertAll(list: List<TerceiroRoomModel>)

    @Query("DELETE FROM $TB_TERCEIRO")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_TERCEIRO WHERE cpfTerceiro = :cpf")
    suspend fun check(cpf: String): Int


    @Query("SELECT * FROM $TB_TERCEIRO")
    suspend fun getAll(): List<TerceiroRoomModel>


    @Query("SELECT * FROM $TB_TERCEIRO WHERE cpfTerceiro = :cpf")
    suspend fun get(cpf: String): List<TerceiroRoomModel>

    @Query("SELECT * FROM $TB_TERCEIRO WHERE idBDTerceiro = :id")
    suspend fun get(id: Int): List<TerceiroRoomModel>

}