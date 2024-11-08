package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.MovEquipResidenciaApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipResidenciaRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput

class MovEquipResidenciaRetrofitDatasourceImpl(
    private val api: MovEquipResidenciaApi
) : MovEquipResidenciaRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipResidenciaRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipResidenciaRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRetrofitDatasourceImpl.send",
                    cause = e
                )
            )
        }
    }

}