package br.com.usinasantafe.pcpcomp.domain.usecases.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.utils.token

interface GetAllColabServer {
    suspend operator fun invoke(): Result<List<Colab>>
}

class GetAllColabServerImpl(
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository
): GetAllColabServer {

    override suspend fun invoke(): Result<List<Colab>> {
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
            val recoverAll = colabRepository.recoverAll(token)
            if(recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverColabServer",
                    cause = e
                )
            )
        }
    }

}