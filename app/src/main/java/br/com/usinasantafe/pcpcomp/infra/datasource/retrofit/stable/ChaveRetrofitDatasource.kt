package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ChaveRetrofitModel

interface ChaveRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<ChaveRetrofitModel>>
}