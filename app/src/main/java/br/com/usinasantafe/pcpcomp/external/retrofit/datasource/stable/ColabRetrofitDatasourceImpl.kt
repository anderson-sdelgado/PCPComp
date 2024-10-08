package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.ColabRetrofitDatasource

class ColabRetrofitDatasourceImpl(
    private val colabApi: ColabApi
): ColabRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<Colab>> {
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