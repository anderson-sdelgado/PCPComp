package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.utils.FlagUpdate

interface SetCheckUpdateAllTable {
    suspend operator fun invoke(flagUpdate: FlagUpdate): Result<Boolean>
}


class SetCheckUpdateAllTableImpl (
    private val configRepository: ConfigRepository,
): SetCheckUpdateAllTable {

    override suspend fun invoke(flagUpdate: FlagUpdate): Result<Boolean> {
        try {
            val resultConfig = configRepository.getConfig()
            if(resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            config.flagUpdate = flagUpdate
            val resultSave = configRepository.save(config)
            if(resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "SetCheckUpdateAllTable",
                    cause = e
                )
            )
        }
    }

}