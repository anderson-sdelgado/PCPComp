package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository

interface SetMatricVigiaConfig {
    suspend operator fun invoke(matric: String): Result<Boolean>
}

class SetMatricVigiaConfigImpl(
    private val configRepository: ConfigRepository,
): SetMatricVigiaConfig {

    override suspend fun invoke(matric: String): Result<Boolean> {
        try {
            val resultConfig = configRepository.getConfig()
            if(resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            config.matricVigia = matric.toInt()
            val resultSave = configRepository.save(config)
            if(resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SetMatricVigiaConfig",
                    cause = e
                )
            )
        }
    }

}