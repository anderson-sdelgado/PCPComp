package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.FluxoApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.FluxoRetrofitModel

class FluxoRetrofitDatasourceImpl(
    private val fluxoApi: FluxoApi
): FluxoRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<FluxoRetrofitModel>> {
        try {
            val response = fluxoApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "FluxoRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}