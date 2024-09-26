package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetMotoristaResidencia {
    suspend operator fun invoke(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetMotoristaResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData
) : SetMotoristaResidencia {

    override suspend fun invoke(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val resultSet = movEquipResidenciaRepository.setMotorista(
            motorista = motorista,
            flowApp = flowApp,
            id = id
        )
        if (resultSet.isFailure)
            return Result.failure(resultSet.exceptionOrNull()!!)
        if(flowApp == FlowApp.CHANGE)
            startProcessSendData()
        return Result.success(true)
    }

}