package br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.utils.token

interface RecoverTerceiroServer {
    suspend operator fun invoke(): Result<List<Terceiro>>
}

class RecoverTerceiroServerImpl(
    private val configRepository: ConfigRepository,
    private val terceiroRepository: TerceiroRepository
): RecoverTerceiroServer {

    override suspend fun invoke(): Result<List<Terceiro>> {
        try {
            val getConfig = configRepository.getConfig()
            if(getConfig.isFailure)
                return Result.failure(getConfig.exceptionOrNull()!!)
            val config = getConfig.getOrNull()!!
            val token = token(
                number = config.number!!,
                version = config.version!!,
                idBD = config.idBD!!
            )
            val recoverAll = terceiroRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(UsecaseException(cause = e))
        }
    }

}
