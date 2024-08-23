package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository

interface SetIdLocalConfig {
    suspend operator fun invoke(idLocal: Int): Result<Boolean>
}

class SetIdLocalConfigImpl(
    private val configRepository: ConfigRepository,
): SetIdLocalConfig {

    override suspend fun invoke(idLocal: Int): Result<Boolean> {
        try {
            val resultConfig = configRepository.getConfig()
            if(resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            config.idLocal = idLocal
            val resultSave = configRepository.save(config)
            if(resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SetIdLocalConfig",
                    cause = e
                )
            )
        }
    }

}