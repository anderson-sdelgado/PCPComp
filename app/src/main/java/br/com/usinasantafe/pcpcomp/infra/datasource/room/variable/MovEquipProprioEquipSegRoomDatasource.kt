package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

interface MovEquipProprioEquipSegRoomDatasource {
    suspend fun add(idEquip: Int, idMov: Int): Result<Boolean>
    suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): Result<Boolean>
    suspend fun delete(idEquip: Int, idMov: Int): Result<Boolean>
    suspend fun list(id: Int): Result<List<MovEquipProprioEquipSegRoomModel>>
}