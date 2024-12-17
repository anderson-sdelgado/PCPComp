package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel

interface MovChaveRoomDatasource {
    suspend fun get(id: Int): Result<MovChaveRoomModel>
    suspend fun listInside(): Result<List<MovChaveRoomModel>>
    suspend fun listOpen(): Result<List<MovChaveRoomModel>>
    suspend fun save(movChaveRoomModel: MovChaveRoomModel): Result<Long>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setIdChave(
        idChave: Int,
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