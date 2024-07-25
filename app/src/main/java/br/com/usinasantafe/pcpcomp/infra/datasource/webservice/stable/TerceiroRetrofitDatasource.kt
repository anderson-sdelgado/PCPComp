package br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel

interface TerceiroRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<TerceiroRoomModel>>
}