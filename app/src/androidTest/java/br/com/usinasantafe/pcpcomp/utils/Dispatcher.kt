package br.com.usinasantafe.pcpcomp.utils

import br.com.usinasantafe.pcpcomp.resultTokenRetrofit
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


val dispatcherSuccessFunctional: Dispatcher = object : Dispatcher() {

    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        when (request.path) {
            "/find-token.php" -> return MockResponse().setBody(resultTokenRetrofit)
            "/colab.php" -> return MockResponse().setBody(returnDataServerColab)
            "/equip.php" -> return MockResponse().setBody(returnDataServerEquip)
            "/fluxo.php" -> return MockResponse().setBody(returnDataServerFluxo)
            "/local.php" -> return MockResponse().setBody(returnDataServerLocal)
            "/r_local_fluxo.php" -> return MockResponse().setBody(returnDataServerRLocalFluxo)
            "/terceiro.php" -> return MockResponse().setBody(returnDataServerTerceiro)
            "/visitante.php" -> return MockResponse().setBody(returnDataServerVisitante)
            "/save-mov-equip-proprio.php" -> return MockResponse().setBody(
                returnDataSendMovEquipProprio
            )
            "/save-mov-equip-visit-terc.php" -> return MockResponse().setBody(
                returnDataSendMovEquipVisitTerc
            )
            "/save-mov-equip-residencia.php" -> return MockResponse().setBody(
                returnDataSendMovEquipResidencia
            )
        }
        return MockResponse().setResponseCode(404)
    }
}
