package br.com.usinasantafe.pcpcomp.infra.datasource.webservice.variable

import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelOutput

interface ConfigRetrofitDatasource {
    suspend fun recoverToken(config: ConfigWebServiceModelOutput): Result<ConfigWebServiceModelInput>
}