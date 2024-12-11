package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel

interface MovChaveRoomDatasource {
    suspend fun listRemove(): Result<List<MovChaveRoomModel>>
}