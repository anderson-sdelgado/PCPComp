package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel

interface LocalRoomDatasource {
    suspend fun addAll(list: List<LocalRoomModel>): Result<Boolean>
    suspend fun getAll(): Result<List<LocalRoomModel>>
    suspend fun getDescr(id: Long): Result<String>
    suspend fun deleteAll(): Result<Boolean>
}