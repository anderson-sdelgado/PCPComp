package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.MovEquipProprioRetrofitModelOutput

interface MovEquipProprioRetrofitDatasource {

    suspend fun send(
        list: List<MovEquipProprioRetrofitModelOutput>
        , token: String)
            : Result<List<MovEquipProprioRetrofitModelInput>>

}