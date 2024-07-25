package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.presenter.config.ConfigModel
import br.com.usinasantafe.pcpcomp.presenter.config.toConfigModel

interface RecoverConfigInternal {
    suspend operator fun invoke(): Result<ConfigModel?>
}

class RecoverConfigInternalImpl(
    private val configRepository: ConfigRepository
): RecoverConfigInternal {

    override suspend fun invoke(): Result<ConfigModel?> {
        try{
            val checkHasConfig = configRepository.hasConfig()
            if(checkHasConfig.isFailure)
                return Result.failure(checkHasConfig.exceptionOrNull()!!)
            if (!checkHasConfig.getOrNull()!!)
                return Result.success(null)
            val config = configRepository.getConfig()
            if(config.isFailure)
                return Result.failure(config.exceptionOrNull()!!)
            return Result.success(config.getOrNull()!!.toConfigModel())
        } catch (e: Exception) {
            return Result.failure(UsecaseException(cause = e))
        }

    }

}
