package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveEquipRoomModel

interface MovChaveEquipRoomDatasource {
    suspend fun get(id: Int): Result<MovChaveEquipRoomModel>
    suspend fun listInside(): Result<List<MovChaveEquipRoomModel>>
    suspend fun listOpen(): Result<List<MovChaveEquipRoomModel>>
    suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setIdEquip(
        idEquip: Int,
        id: Int
    ): Result<Boolean>
    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>
    suspend fun setMatricColab(
        matric: Int,
        id: Int
    ): Result<Boolean>
    suspend fun setOutside(id: Int): Result<Boolean>
}