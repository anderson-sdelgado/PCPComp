package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.BuildConfig
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.usecases.background.StartProcessSendData

interface AdjustConfig {
    suspend operator fun invoke(version: String): Result<Boolean>
}

class AdjustConfigImpl(
    private val configRepository: ConfigRepository,
    private val startProcessSendData: StartProcessSendData
): AdjustConfig {

    override suspend fun invoke(version: String): Result<Boolean> {
        try {
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure)
                return Result.failure(resultHasConfig.exceptionOrNull()!!)
            val hasConfig = resultHasConfig.getOrNull()!!
            if (hasConfig) {
                val resultConfig = configRepository.getConfig()
                if (resultConfig.isFailure)
                    return Result.failure(resultConfig.exceptionOrNull()!!)
                val config = resultConfig.getOrNull()!!
                config.version?.let {
                    if(it != BuildConfig.VERSION_NAME){
                        val resultClean = configRepository.cleanConfig()
                        if (resultClean.isFailure)
                            return Result.failure(resultClean.exceptionOrNull()!!)
                    }
                }
            }
            startProcessSendData()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartAppImpl",
                    cause = e
                )
            )
        }
    }

}