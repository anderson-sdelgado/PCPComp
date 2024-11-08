package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.FluxoRetrofitModel

interface FluxoRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<FluxoRetrofitModel>>
}