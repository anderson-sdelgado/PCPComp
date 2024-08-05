package br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel

interface ColabRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<Colab>>
}