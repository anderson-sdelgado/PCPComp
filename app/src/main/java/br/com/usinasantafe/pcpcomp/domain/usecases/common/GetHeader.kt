package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.pcpcomp.presenter.model.HeaderModel

interface GetHeader {
    suspend operator fun invoke(): Result<HeaderModel>
}

class GetHeaderImpl(
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository,
    private val localRepository: LocalRepository,
): GetHeader {

    override suspend fun invoke(): Result<HeaderModel> {
        try {
            val resultConfig = configRepository.getConfig()
            if(resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val config = resultConfig.getOrNull()!!
            val resultNomeColab = colabRepository.getNome(config.matricVigia!!)
            if(resultNomeColab.isFailure)
                return Result.failure(resultNomeColab.exceptionOrNull()!!)
            val nomeColab = resultNomeColab.getOrNull()!!
            val resultLocal = localRepository.getDescr(config.idLocal!!)
            if(resultLocal.isFailure)
                return Result.failure(resultLocal.exceptionOrNull()!!)
            val descrLocal = resultLocal.getOrNull()!!
            return Result.success(
                HeaderModel(
                    descrVigia = "${config.matricVigia} - $nomeColab",
                    descrLocal = descrLocal,
                )
            )
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "RecoverHeader",
                    cause = e
                )
            )
        }
    }

}