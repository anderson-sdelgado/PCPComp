package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException

interface CheckPasswordConfig {
    suspend operator fun invoke(password: String): Result<Boolean>
}

class ICheckPasswordConfig(
    private val configRepository: ConfigRepository
): CheckPasswordConfig {

    override suspend fun invoke(password: String): Result<Boolean> {
        try {
            val checkHasConfig = configRepository.hasConfig()

            if(checkHasConfig.isFailure)
                return checkHasConfig

            if (!checkHasConfig.getOrNull()!!)
                return Result.success(true)

            val passwordBD = configRepository.getPassword()
            if(passwordBD.isFailure)
                return Result.failure(passwordBD.exceptionOrNull()!!)

            if (passwordBD.getOrNull() == password)
                return Result.success(true)

            return Result.success(false)

        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckPasswordConfig",
                    cause = e
                )
            )
        }
    }

}