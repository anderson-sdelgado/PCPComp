package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

interface MovEquipProprioEquipSegRoomDatasource {
    suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): Result<Boolean>
}