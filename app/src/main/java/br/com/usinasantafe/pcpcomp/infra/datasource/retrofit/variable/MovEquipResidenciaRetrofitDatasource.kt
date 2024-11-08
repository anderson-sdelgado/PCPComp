package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput

interface MovEquipResidenciaRetrofitDatasource {

    suspend fun send(
        list: List<MovEquipResidenciaRetrofitModelOutput>
        , token: String)
            : Result<List<MovEquipResidenciaRetrofitModelInput>>

}