package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.MovEquipVisitTercApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipVisitTercRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput

class IMovEquipVisitTercRetrofitDatasource(
    private val api: MovEquipVisitTercApi
): MovEquipVisitTercRetrofitDatasource {

    override suspend fun send(
        list: List<MovEquipVisitTercRetrofitModelOutput>,
        token: String
    ): Result<List<MovEquipVisitTercRetrofitModelInput>> {
        try {
            val response = api.send(
                auth = token,
                data = list
            )
            return Result.success(response.body()!!)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRetrofitDatasourceImpl.send",
                    cause = e
                )
            )
        }
    }

}