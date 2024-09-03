package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.LocalApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.LocalRetrofitDatasource

class LocalRetrofitDatasourceImpl(
    private val localApi: LocalApi
): LocalRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<Local>> {
        try {
            val response = localApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "LocalRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}