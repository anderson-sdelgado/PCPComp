package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab

interface ColabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<Colab>>
}