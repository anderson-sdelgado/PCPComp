package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetVeiculoResidencia {
    suspend operator fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class SetVeiculoResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData
) : SetVeiculoResidencia {

    override suspend fun invoke(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        val resultSet = movEquipResidenciaRepository.setVeiculo(
            veiculo = veiculo,
            flowApp = flowApp,
            id = id
        )
        if (resultSet.isFailure)
            return Result.failure(resultSet.exceptionOrNull()!!)
        if (flowApp == FlowApp.CHANGE)
            startProcessSendData()
        return Result.success(true)
    }

}