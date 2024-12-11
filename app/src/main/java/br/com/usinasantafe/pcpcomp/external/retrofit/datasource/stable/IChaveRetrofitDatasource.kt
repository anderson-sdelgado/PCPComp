package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.ChaveApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ChaveRetrofitModel

class IChaveRetrofitDatasource(
    private val chaveApi: ChaveApi
): ChaveRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<ChaveRetrofitModel>> {
        try {
            val response = chaveApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "IChaveRetrofitDatasource.recoverAll",
                    cause = e
                )
            )
        }
    }

}