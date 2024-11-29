package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ColabRetrofitModel

class IColabRetrofitDatasource(
    private val colabApi: ColabApi
): ColabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<ColabRetrofitModel>> {
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