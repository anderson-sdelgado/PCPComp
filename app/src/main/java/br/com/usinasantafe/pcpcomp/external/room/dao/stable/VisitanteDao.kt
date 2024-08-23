package br.com.usinasantafe.pcpcomp.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import br.com.usinasantafe.pcpcomp.utils.TB_VISITANTE

@Dao
interface VisitanteDao {

    @Insert
    fun insertAll(list: List<VisitanteRoomModel>)

    @Query("DELETE FROM $TB_VISITANTE")
    suspend fun deleteAll()

    @Query("SELECT count(*) FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun checkCPFVisitante(cpf: String): Int

    @Query("SELECT * FROM $TB_VISITANTE WHERE cpfVisitante = :cpf")
    suspend fun getVisitanteCPF(cpf: String): VisitanteRoomModel

    @Query("SELECT * FROM $TB_VISITANTE WHERE idVisitante = :id")
    suspend fun getVisitanteId(id: Int): VisitanteRoomModel

}