package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel

interface MovEquipProprioPassagRoomDatasource {
    suspend fun addAll(list: List<MovEquipProprioPassagRoomModel>): Result<Boolean>
}