package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel

interface MovEquipProprioRoomDatasource {
    suspend fun listOpen(): Result<List<MovEquipProprioRoomModel>>
    suspend fun save(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Long>
    suspend fun setClose(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Boolean>
}