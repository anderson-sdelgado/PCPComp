package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException

interface SendDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String
    ): Result<Long>
}

class SendDataConfigImpl (
    private val configRepository: ConfigRepository
) : SendDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String
    ): Result<Long> {
        try {
            val config = Config(
                number = number.toLong(),
                password = password,
                version = version,
            )
            val result = configRepository.send(config)
            result.fold(
                onSuccess = {
                    return Result.success(it)
                },
                onFailure = { exception ->
                    return Result.failure(exception)
                }
            )
        } catch (e: Exception){
            return Result.failure(UsecaseException(cause = e))
        }
    }

}


