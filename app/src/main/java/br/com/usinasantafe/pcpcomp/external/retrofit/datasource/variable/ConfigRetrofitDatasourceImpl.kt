package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.ConfigWebServiceModelOutput

class ConfigRetrofitDatasourceImpl(
    private val configApi: ConfigApi
): ConfigRetrofitDatasource {

        override suspend fun recoverToken(config: ConfigWebServiceModelOutput): Result<ConfigWebServiceModelInput> {
        try {
            val response = configApi.send(config)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ConfigRetrofitDatasourceImpl.recoverToken",
                    cause = e
                )
            )
        }
    }

}