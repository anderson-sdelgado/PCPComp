package br.com.usinasantafe.pcpcomp.external.retrofit.api.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.VisitanteRetrofitModel
import br.com.usinasantafe.pcpcomp.utils.WEB_ALL_VISITANTE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface VisitanteApi {

    @GET(WEB_ALL_VISITANTE)
    suspend fun all(@Header("Authorization") auth: String): Response<List<VisitanteRetrofitModel>>

}