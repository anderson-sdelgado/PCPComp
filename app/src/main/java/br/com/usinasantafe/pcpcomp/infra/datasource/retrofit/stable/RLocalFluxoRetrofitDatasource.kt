package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel

interface RLocalFluxoRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<RLocalFluxoRetrofitModel>>
}