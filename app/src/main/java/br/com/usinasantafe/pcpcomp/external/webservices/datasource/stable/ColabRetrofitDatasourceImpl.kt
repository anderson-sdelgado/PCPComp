package br.com.usinasantafe.pcpcomp.external.webservices.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.webservices.api.stable.ColabApi
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel

class ColabRetrofitDatasourceImpl(
    private val colabApi: ColabApi
): ColabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<ColabRoomModel>> {
        try {
            val response = colabApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "ColabRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}