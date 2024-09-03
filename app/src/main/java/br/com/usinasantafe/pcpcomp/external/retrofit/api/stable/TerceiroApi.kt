package br.com.usinasantafe.pcpcomp.external.retrofit.api.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.utils.WEB_ALL_TERCEIRO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TerceiroApi {

    @GET(WEB_ALL_TERCEIRO)
    suspend fun all(@Header("Authorization") auth: String): Response<List<Terceiro>>

}