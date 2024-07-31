package br.com.usinasantafe.pcpcomp.external.webservices.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.webservices.api.variable.ConfigApi
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelOutput

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