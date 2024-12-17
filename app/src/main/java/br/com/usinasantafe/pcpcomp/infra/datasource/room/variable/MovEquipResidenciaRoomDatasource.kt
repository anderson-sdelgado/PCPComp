package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel

interface MovEquipResidenciaRoomDatasource {
    suspend fun checkOpen(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean>
    suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel>
    suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listInside(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listSend(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listSent(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun save(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Long>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setMotorista(
        motorista: String,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean>

    suspend fun setOutside(id: Int): Result<Boolean>
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