package br.com.usinasantafe.pcpcomp.external.webservices.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.webservices.api.stable.LocalApi
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel

class LocalRetrofitDatasourceImpl(
    private val localApi: LocalApi
): LocalRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<LocalRoomModel>> {
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