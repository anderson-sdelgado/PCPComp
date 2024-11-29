package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.RLocalFluxoApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel

class IRLocalFluxoRetrofitDatasource(
    private val rLocalFluxoApi: RLocalFluxoApi
): RLocalFluxoRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<RLocalFluxoRetrofitModel>> {
        try {
            val response = rLocalFluxoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "RLocalFluxoRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}