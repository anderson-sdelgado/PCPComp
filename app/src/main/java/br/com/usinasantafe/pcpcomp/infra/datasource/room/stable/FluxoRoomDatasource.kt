package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.FluxoRoomModel

interface FluxoRoomDatasource {
    suspend fun addAll(list: List<FluxoRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<FluxoRoomModel>
}