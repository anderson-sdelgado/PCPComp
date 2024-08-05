package br.com.usinasantafe.pcpcomp.external.webservices.api.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcpcomp.utils.WEB_ALL_EQUIP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface EquipApi {

    @GET(WEB_ALL_EQUIP)
    suspend fun all(@Header("Authorization") auth: String): Response<List<Equip>>

}