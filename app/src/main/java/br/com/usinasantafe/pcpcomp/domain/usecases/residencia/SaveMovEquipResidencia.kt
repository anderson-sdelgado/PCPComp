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
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean>
}

class SaveMovEquipResidenciaImpl(
    private val configRepository: ConfigRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
    private val startProcessSendData: StartProcessSendData,
    private val setStatusOutsideMovResidencia: SetStatusOutsideMovResidencia
) : SaveMovEquipResidencia {

    override suspend fun invoke(
        typeMov: TypeMov,
        id: Int
    ): Result<Boolean> {
        try {
            if (typeMov == TypeMov.OUTPUT) {
                val resultClose = setStatusOutsideMovResidencia(id)
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
            val idSave = resultSave.getOrNull()!!
            if (typeMov == TypeMov.OUTPUT) {
                val resultClose = setStatusOutsideMovResidencia(idSave)
                if (resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
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