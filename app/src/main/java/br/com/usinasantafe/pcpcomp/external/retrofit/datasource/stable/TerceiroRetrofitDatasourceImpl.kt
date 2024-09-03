package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.TerceiroApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource

class TerceiroRetrofitDatasourceImpl(
    private val terceiroApi: TerceiroApi
): TerceiroRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<Terceiro>> {
        try {
            val response = terceiroApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "TerceiroRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}