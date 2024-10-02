package br.com.usinasantafe.pcpcomp.infra.datasource.room.variable

import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel

interface MovEquipResidenciaRoomDatasource {
    suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel>
    suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>>
    suspend fun listInputOpen(): Result<List<MovEquipResidenciaRoomModel>>
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

    suspend fun setPlaca(
        placa: String,
        id: Int
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        id: Int
    ): Result<Boolean>

}