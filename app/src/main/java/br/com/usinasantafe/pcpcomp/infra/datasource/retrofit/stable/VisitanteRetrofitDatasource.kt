package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.VisitanteRetrofitModel

interface VisitanteRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<VisitanteRetrofitModel>>
}