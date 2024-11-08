package br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable

import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.ConfigWebServiceModelOutput

interface ConfigRetrofitDatasource {
    suspend fun recoverToken(config: ConfigWebServiceModelOutput): Result<ConfigWebServiceModelInput>
}