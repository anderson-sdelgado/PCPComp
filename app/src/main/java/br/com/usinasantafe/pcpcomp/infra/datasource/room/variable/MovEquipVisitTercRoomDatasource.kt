package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel

interface MovEquipVisitTercRoomDatasource {
    suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun setClose(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean>
}