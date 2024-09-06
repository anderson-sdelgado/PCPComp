package br.com.usinasantafe.pcpcomp.domain.usecases.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.utils.token

interface GetAllEquipServer {
    suspend operator fun invoke(): Result<List<Equip>>
}

class GetAllEquipServerImpl(
    private val configRepository: ConfigRepository,
    private val equipRepository: EquipRepository
): GetAllEquipServer {

    override suspend fun invoke(): Result<List<Equip>> {
        try {
            val getConfig = configRepository.getConfig()
            if(getConfig.isFailure)
                return Result.failure(getConfig.exceptionOrNull()!!)
            val config = getConfig.getOrNull()!!
            val token = token(
                number = config.number!!,
                version = config.version!!,
                idBD = config.idBD!!
            )
            val recoverAll = equipRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverEquipServer",
                    cause = e
                )
            )
        }
    }

}
