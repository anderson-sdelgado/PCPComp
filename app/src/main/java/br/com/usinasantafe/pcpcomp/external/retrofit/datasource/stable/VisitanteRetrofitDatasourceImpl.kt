package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.VisitanteApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource

class VisitanteRetrofitDatasourceImpl(
    private val visitanteApi: VisitanteApi
): VisitanteRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<Visitante>> {
        try {
            val response = visitanteApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "VisitanteRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}