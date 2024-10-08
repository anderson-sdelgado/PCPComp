package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel

interface MovEquipResidenciaRoomDatasource {
    suspend fun checkSend(): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel>
    suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listInside(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listSend(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun save(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Long>
    suspend fun setClose(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean>
    suspend fun setMotorista(
        motorista: String,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>

    suspend fun setOutside(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean>
    suspend fun setPlaca(
        placa: String,
        id: Int
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        id: Int
    ): Result<Boolean>

    suspend fun setSent(id: Int): Result<Boolean>

}