package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput

interface MovEquipVisitTercRetrofitDatasource {

    suspend fun send(
        list: List<MovEquipVisitTercRetrofitModelOutput>
        , token: String)
            : Result<List<MovEquipVisitTercRetrofitModelInput>>

}