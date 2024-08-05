package br.com.usinasantafe.pcpcomp.external.webservices.api.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import br.com.usinasantafe.pcpcomp.utils.WEB_ALL_LOCAL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LocalApi {

    @GET(WEB_ALL_LOCAL)
    suspend fun all(@Header("Authorization") auth: String): Response<List<Local>>

}