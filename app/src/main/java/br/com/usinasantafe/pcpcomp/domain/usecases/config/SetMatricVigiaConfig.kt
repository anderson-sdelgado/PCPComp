package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository

interface SetMatricVigiaConfig {
    suspend operator fun invoke(matric: String): Result<Boolean>
}

class ISetMatricVigiaConfig(
    private val configRepository: ConfigRepository,
): SetMatricVigiaConfig {

    override suspend fun invoke(matric: String): Result<Boolean> {
        return try {
            configRepository.setMatricVigia(matric.toInt())
        } catch (e: Exception){
            Result.failure(
                UsecaseException(
                    function = "SetMatricVigiaConfig",
                    cause = e
                )
            )
        }
    }

}