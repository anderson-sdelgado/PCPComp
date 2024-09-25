package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

interface SetObservVisitTerc {
    suspend operator fun invoke(
        observ: String?,
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean>
}

class SetObservVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val startMovEquipVisitTerc: StartMovEquipVisitTerc,
    private val startProcessSendData: StartProcessSendData
) : SetObservVisitTerc {

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
                val resultMov = movEquipVisitTercRepository.get(id)
                if (resultMov.isFailure)
                    return Result.failure(resultMov.exceptionOrNull()!!)
                val passag = movEquipVisitTercPassagRepository.list(
                    flowApp = FlowApp.CHANGE,
                    id = id
                )
                if (passag.isFailure)
                    return Result.failure(passag.exceptionOrNull()!!)
                val mov = resultMov.getOrNull()!!
                mov.observMovEquipVisitTerc = observ
                mov.tipoMovEquipVisitTerc = TypeMov.OUTPUT
                mov.dthrMovEquipVisitTerc = Date()
                mov.destinoMovEquipVisitTerc = null
                mov.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
                mov.movEquipVisitTercPassagList = passag.getOrNull()!!
                val resultStart = startMovEquipVisitTerc(movEquipVisitTerc = mov)
                if (resultStart.isFailure)
                    return Result.failure(resultStart.exceptionOrNull()!!)
                return Result.success(true)
            }
            val resultSet = movEquipVisitTercRepository.setObservVisitTerc(
                observ = observ,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            if(flowApp == FlowApp.CHANGE)
                startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SetObservVisitTercImpl",
                    cause = e.cause
                )
            )
        }
    }

}