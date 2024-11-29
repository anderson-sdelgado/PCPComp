package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.token

interface SendMovVisitTercList {
    suspend operator fun invoke(): Result<List<MovEquipVisitTerc>>
}

class ISendMovVisitTercList(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val configRepository: ConfigRepository,
) : SendMovVisitTercList {

    override suspend fun invoke(): Result<List<MovEquipVisitTerc>> {
        try {
            val resultListSend = movEquipVisitTercRepository.listSend()
            if (resultListSend.isFailure)
                return Result.failure(resultListSend.exceptionOrNull()!!)
            val listSend = resultListSend.getOrNull()!!
            val listSendFull = listSend.map { entity ->
                val resultListPassag = movEquipVisitTercPassagRepository.list(
                    FlowApp.CHANGE,
                    entity.idMovEquipVisitTerc!!
                )
                if (resultListPassag.isFailure)
                    return Result.failure(resultListPassag.exceptionOrNull()!!)
                entity.movEquipVisitTercPassagList = resultListPassag.getOrNull()!!
                return@map entity
            }
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val token = token(
                number = config.number!!,
                version = config.version!!,
                idBD = config.idBD!!
            )
            val resultSend = movEquipVisitTercRepository.send(
                list = listSendFull,
                number = config.number!!,
                token = token
            )
            if (resultSend.isFailure)
                return Result.failure(resultSend.exceptionOrNull()!!)
            return Result.success(resultSend.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "SendMovVisitTercListImpl",
                    cause = e
                )
            )
        }
    }

}