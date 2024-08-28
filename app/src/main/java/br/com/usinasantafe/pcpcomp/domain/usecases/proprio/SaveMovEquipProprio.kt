package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.StatusSend

interface SaveMovEquipProprio {
    suspend operator fun invoke(): Result<Boolean>
}

class SaveMovEquipProprioImpl(
    private val configRepository: ConfigRepository,
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val startProcessSendData: StartProcessSendData
) : SaveMovEquipProprio {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultSave = movEquipProprioRepository.save(config.matricVigia!!, config.idLocal!!)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!
            if (id == 0)
                return Result.failure(
                    UsecaseException(
                        function = "SaveMovEquipProprioImpl",
                        cause = Exception("Id is 0")
                    )
                )
            val resultSavePassag = movEquipProprioPassagRepository.save(id)
            if (resultSavePassag.isFailure)
                return Result.failure(resultSavePassag.exceptionOrNull()!!)
            val resultSaveEquipSeg = movEquipProprioEquipSegRepository.save(id)
            if (resultSaveEquipSeg.isFailure)
                return Result.failure(resultSaveEquipSeg.exceptionOrNull()!!)
            val resultSetStatusSend = configRepository.setStatusSend(StatusSend.SEND)
            if (resultSetStatusSend.isFailure)
                return Result.failure(resultSetStatusSend.exceptionOrNull()!!)
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SaveMovEquipProprioImpl",
                    cause = e
                )
            )
        }
    }

}