package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel

interface VisitanteRoomDatasource {
    suspend fun addAll(list: List<VisitanteRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}