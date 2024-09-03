package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local

interface LocalRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<Local>>
}