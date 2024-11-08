package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel

interface RLocalFluxoRoomDatasource {
    suspend fun addAll(list: List<RLocalFluxoRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}