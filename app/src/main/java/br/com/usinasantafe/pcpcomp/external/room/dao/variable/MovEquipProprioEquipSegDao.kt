package br.com.usinasantafe.pcpcomp.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

@Dao
interface MovEquipProprioEquipSegDao {

    @Insert
    suspend fun insertAll(list: List<MovEquipProprioEquipSegRoomModel>)

}