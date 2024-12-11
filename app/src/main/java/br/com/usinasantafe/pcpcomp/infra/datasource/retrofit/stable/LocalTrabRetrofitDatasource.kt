package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.LocalTrabRetrofitModel

interface LocalTrabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<LocalTrabRetrofitModel>>
}
