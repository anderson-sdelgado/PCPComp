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
            return configRepository.setIdLocal(idLocal)
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