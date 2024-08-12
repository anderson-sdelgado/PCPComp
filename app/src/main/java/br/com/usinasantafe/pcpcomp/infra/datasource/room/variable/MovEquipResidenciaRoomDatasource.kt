package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel

interface MovEquipResidenciaRoomDatasource {
    suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun setClose(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean>
}