package br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel

interface VisitanteRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<VisitanteRoomModel>>
}