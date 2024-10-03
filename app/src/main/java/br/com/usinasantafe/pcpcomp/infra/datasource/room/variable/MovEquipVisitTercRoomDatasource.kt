package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel

interface MovEquipVisitTercRoomDatasource {
    suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel>
    suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun listInside(): Result<List<MovEquipVisitTercRoomModel>>
    suspend fun save(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Long>
    suspend fun setClose(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean>
    suspend fun setDestino(
        destino: String,
        id: Int
    ): Result<Boolean>

    suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>

    suspend fun setOutside(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean>
    suspend fun setPlaca(
        placa: String,
        id: Int
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        id: Int
    ): Result<Boolean>
}