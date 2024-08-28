package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel

@Dao
interface MovEquipProprioPassagDao {

    @Insert
    suspend fun insertAll(list: List<MovEquipProprioPassagRoomModel>)

}