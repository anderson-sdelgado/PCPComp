package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.token

interface SendMovProprioList {
    suspend operator fun invoke(): Result<List<MovEquipProprio>>
}

class ISendMovProprioList(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val configRepository: ConfigRepository,
) : SendMovProprioList {

    override suspend fun invoke(): Result<List<MovEquipProprio>> {
        try {
            val resultListSend = movEquipProprioRepository.listSend()
            if (resultListSend.isFailure)
                return Result.failure(resultListSend.exceptionOrNull()!!)
            val listSend = resultListSend.getOrNull()!!
            val listSendFull = listSend.map { entity ->
                val resultListEquipSeg = movEquipProprioEquipSegRepository.list(
                    FlowApp.CHANGE,
                    entity.idMovEquipProprio!!
                )
                if (resultListEquipSeg.isFailure)
                    return Result.failure(resultListEquipSeg.exceptionOrNull()!!)
                entity.movEquipProprioEquipSegList = resultListEquipSeg.getOrNull()!!
                val resultListPassag = movEquipProprioPassagRepository.list(
                    FlowApp.CHANGE,
                    entity.idMovEquipProprio!!
                )
                if (resultListPassag.isFailure)
                    return Result.failure(resultListPassag.exceptionOrNull()!!)
                entity.movEquipProprioPassagList = resultListPassag.getOrNull()!!
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
            val resultSend = movEquipProprioRepository.send(
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
                    function = "SendMovProprioListImpl",
                    cause = e
                )
            )
        }
    }

}