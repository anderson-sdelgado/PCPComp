package br.com.usinasantafe.pcpcomp.domain.usecases.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.utils.token

interface GetAllLocalServer {
    suspend operator fun invoke(): Result<List<Local>>
}

class GetAllLocalServerImpl(
    private val configRepository: ConfigRepository,
    private val localRepository: LocalRepository
): GetAllLocalServer {

    override suspend fun invoke(): Result<List<Local>> {
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
            val recoverAll = localRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverLocalServer",
                    cause = e
                )
            )
        }
    }

}
