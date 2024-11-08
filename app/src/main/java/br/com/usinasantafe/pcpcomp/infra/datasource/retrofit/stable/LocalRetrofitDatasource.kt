package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.LocalRetrofitModel

interface LocalRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<LocalRetrofitModel>>
}