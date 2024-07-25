package br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel

interface EquipRetrofitDatasource {
    suspend fun recoverAll(token: String): Result<List<EquipRoomModel>>
}