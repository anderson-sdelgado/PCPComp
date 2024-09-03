package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.MovEquipProprioApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipProprioRetrofitModelOutput

class MovEquipProprioRetrofitDatasourceImpl(
    private val api: MovEquipProprioApi
): MovEquipProprioRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipProprioRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipProprioRetrofitModelInput>> {
        try {
            val response = api.send(auth = token, data = list)
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRetrofitDatasourceImpl.send",
                    cause = e
                )
            )
        }
    }

}