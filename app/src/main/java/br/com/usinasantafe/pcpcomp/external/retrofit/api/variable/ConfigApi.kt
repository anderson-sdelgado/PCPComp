package br.com.usinasantafe.pcpcomp.external.retrofit.api.variable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.ConfigWebServiceModelOutput
import br.com.usinasantafe.pcpcomp.utils.WEB_SAVE_TOKEN
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConfigApi {

    @POST(WEB_SAVE_TOKEN)
    suspend fun send(@Body config: ConfigWebServiceModelOutput): Response<ConfigWebServiceModelInput>

}