package br.com.usinasantafe.pcpcomp.external.webservices.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.webservices.api.stable.VisitanteApi
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel

class VisitanteRetrofitDatasourceImpl(
    private val visitanteApi: VisitanteApi
): VisitanteRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<VisitanteRoomModel>> {
        try {
            val response = visitanteApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(DatasourceException(cause = e))
        }
    }

}