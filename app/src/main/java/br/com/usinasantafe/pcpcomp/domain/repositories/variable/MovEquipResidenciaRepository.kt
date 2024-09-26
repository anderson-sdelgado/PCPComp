package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovEquipResidenciaRepository {
    suspend fun get(id: Int): Result<MovEquipResidencia>
    suspend fun getMotorista(id: Int): Result<String>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun getPlaca(id: Int): Result<String>
    suspend fun getVeiculo(id: Int): Result<String>
    suspend fun listOpen(): Result<List<MovEquipResidencia>>
    suspend fun listOpenInput(): Result<List<MovEquipResidencia>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>

    suspend fun setClose(movEquipResidencia: MovEquipResidencia): Result<Boolean>
    suspend fun setMotorista(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setPlaca(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun start(): Result<Boolean>
    suspend fun start(movEquipResidencia: MovEquipResidencia): Result<Boolean>
}