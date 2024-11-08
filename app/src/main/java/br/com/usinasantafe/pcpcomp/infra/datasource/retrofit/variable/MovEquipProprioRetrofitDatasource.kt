package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelOutput

interface MovEquipProprioRetrofitDatasource {

    suspend fun send(
        list: List<MovEquipProprioRetrofitModelOutput>
        , token: String)
            : Result<List<MovEquipProprioRetrofitModelInput>>

}