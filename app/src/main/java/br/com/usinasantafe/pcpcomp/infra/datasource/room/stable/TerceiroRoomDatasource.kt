package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel

interface TerceiroRoomDatasource {
    suspend fun addAll(list: List<TerceiroRoomModel>): Result<Boolean>
    suspend fun checkCpf(cpf: String): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<List<TerceiroRoomModel>>
    suspend fun get(cpf: String): Result<List<TerceiroRoomModel>>
}