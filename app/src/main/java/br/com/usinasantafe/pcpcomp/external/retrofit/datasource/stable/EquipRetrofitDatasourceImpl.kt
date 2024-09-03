package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.EquipApi
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.EquipRetrofitDatasource

class EquipRetrofitDatasourceImpl(
    private val equipApi: EquipApi
): EquipRetrofitDatasource {

    override suspend fun recoverAll(token: String): Result<List<Equip>> {
        try {
            val response = equipApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "EquipRetrofitDatasourceImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}