package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData
import br.com.usinasantafe.pcpcomp.utils.StatusSend

interface SaveMovEquipVisitTerc {
    suspend operator fun invoke(
        id: Int = 0
    ): Result<Boolean>
}

class SaveMovEquipVisitTercImpl(
    private val configRepository: ConfigRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val startProcessSendData: StartProcessSendData
) : SaveMovEquipVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        try {
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultSave = movEquipVisitTercRepository.save(
                config.matricVigia!!,
                config.idLocal!!
            )
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!
            val resultSavePassag = movEquipVisitTercPassagRepository.save(id)
            if (resultSavePassag.isFailure)
                return Result.failure(resultSavePassag.exceptionOrNull()!!)
            val resultSetStatusSend = configRepository.setStatusSend(StatusSend.SEND)
            if (resultSetStatusSend.isFailure)
                return Result.failure(resultSetStatusSend.exceptionOrNull()!!)
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SaveMovEquipVisitTercImpl",
                    cause = e
                )
            )
        }
    }

}