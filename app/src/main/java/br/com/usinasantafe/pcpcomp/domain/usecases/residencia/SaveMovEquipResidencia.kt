package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface SaveMovEquipResidencia {
    suspend operator fun invoke(
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean>
}

class SaveMovEquipResidenciaImpl(
    private val configRepository: ConfigRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData,
    private val closeMovResidencia: CloseMovResidencia
) : SaveMovEquipResidencia {

    override suspend fun invoke(
        flowApp: FlowApp,
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean> {
        try {
            if (
                (flowApp == FlowApp.ADD) &&
                (typeMov == TypeMov.OUTPUT)
            ) {
                val resultClose = closeMovResidencia(id)
                if (resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultSave = movEquipResidenciaRepository.save(
                config.matricVigia!!,
                config.idLocal!!
            )
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val resultSetStatusSend = configRepository.setStatusSend(StatusSend.SEND)
            if (resultSetStatusSend.isFailure)
                return Result.failure(resultSetStatusSend.exceptionOrNull()!!)
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SaveMovEquipResidenciaImpl",
                    cause = e
                )
            )
        }
    }

}