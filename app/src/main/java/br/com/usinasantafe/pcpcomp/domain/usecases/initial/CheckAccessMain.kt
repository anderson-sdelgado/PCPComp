package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate

interface CheckAccessMain {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessMain(
    private val configRepository: ConfigRepository
): CheckAccessMain {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val checkHasConfig = configRepository.hasConfig()
            if(checkHasConfig.isFailure)
                return Result.failure(checkHasConfig.exceptionOrNull()!!)
            if (!checkHasConfig.getOrNull()!!)
                return Result.success(false)
            val flagUpdate = configRepository.getFlagUpdate()
            if(flagUpdate.isFailure)
                return Result.failure(flagUpdate.exceptionOrNull()!!)
            if (flagUpdate.getOrNull()!! == FlagUpdate.OUTDATED)
                return Result.success(false)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckAccessMainImpl",
                    cause = e
                )
            )
        }
    }

}