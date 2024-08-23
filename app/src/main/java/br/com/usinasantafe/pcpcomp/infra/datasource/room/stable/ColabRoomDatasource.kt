package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel

interface ColabRoomDatasource {
    suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean>
    suspend fun checkMatric(matric: Int): Result<Boolean>
    suspend fun getNome(matric: Int): Result<String>
    suspend fun deleteAll(): Result<Boolean>
}