package br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel

interface LocalRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<LocalRoomModel>>
}