package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel

interface ChaveRoomDatasource {
    suspend fun addAll(list: List<ChaveRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<ChaveRoomModel>
    suspend fun listAll(): Result<List<ChaveRoomModel>>
}