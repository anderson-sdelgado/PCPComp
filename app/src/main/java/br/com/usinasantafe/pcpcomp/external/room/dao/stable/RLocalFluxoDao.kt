package br.com.usinasantafe.pcpcomp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_R_LOCAL_FLUXO

@Dao
interface RLocalFluxoDao {

    @Insert
    fun insertAll(list: List<RLocalFluxoRoomModel>)

    @Query("DELETE FROM $TB_R_LOCAL_FLUXO")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_R_LOCAL_FLUXO ORDER BY idRLocalFluxo ASC")
    suspend fun listAll(): List<RLocalFluxoRoomModel>

    @Query("SELECT * FROM $TB_R_LOCAL_FLUXO WHERE idLocal = :idLocal ORDER BY idRLocalFluxo ASC")
    suspend fun list(idLocal: Int): List<RLocalFluxoRoomModel>

}