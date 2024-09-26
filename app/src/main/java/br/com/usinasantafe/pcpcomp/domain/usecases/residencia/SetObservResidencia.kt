package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

interface SetObservResidencia {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean>
}

class SetObservResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startMovEquipResidencia: StartMovEquipResidencia,
    private val startProcessSendData: StartProcessSendData
) : SetObservResidencia {

    override suspend fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean> {
        try {
            if (
                (typeMov == TypeMov.OUTPUT) &&
                (flowApp == FlowApp.ADD)
            ) {
                val resultMov = movEquipResidenciaRepository.get(id)
                if (resultMov.isFailure)
                    return Result.failure(resultMov.exceptionOrNull()!!)
                val mov = resultMov.getOrNull()!!
                mov.observMovEquipResidencia = observ
                mov.tipoMovEquipResidencia = TypeMov.OUTPUT
                mov.dthrMovEquipResidencia = Date()
                mov.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
                val resultStart = startMovEquipResidencia(movEquipResidencia = mov)
                if (resultStart.isFailure)
                    return Result.failure(resultStart.exceptionOrNull()!!)
                return Result.success(true)
            }
            val resultSet = movEquipResidenciaRepository.setObserv(
                observ = observ,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SetObservResidenciaImpl",
                    cause = e.cause
                )
            )
        }
    }

}