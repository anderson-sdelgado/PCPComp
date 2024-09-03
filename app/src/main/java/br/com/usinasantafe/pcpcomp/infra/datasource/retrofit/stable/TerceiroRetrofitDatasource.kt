package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro

interface TerceiroRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<Terceiro>>
}