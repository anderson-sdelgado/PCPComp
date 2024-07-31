package br.com.usinasantafe.pcpcomp.external.webservices.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.webservices.api.stable.TerceiroApi
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel

class TerceiroRetrofitDatasourceImpl(
    private val terceiroApi: TerceiroApi
): TerceiroRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<TerceiroRoomModel>> {
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