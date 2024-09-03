package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante

interface VisitanteRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<Visitante>>
}