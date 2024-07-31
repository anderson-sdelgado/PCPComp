package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.ConfigRepository

interface RecoverNomeVigia {
    suspend operator fun invoke(): Result<String>
}

class RecoverNomeVigiaImpl(
    private val configRepository: ConfigRepository,
    private val colabRepository: ColabRepository,
): RecoverNomeVigia {

    override suspend fun invoke(): Result<String> {
        try {
            val resultConfig = configRepository.getConfig()
            if (resultConfig.isFailure)
                return Result.failure(resultConfig.exceptionOrNull()!!)
            val matric = resultConfig.getOrNull()!!.matricVigia!!
            val resultNome = colabRepository.getNome(matric)
            if (resultNome.isFailure)
                return Result.failure(resultNome.exceptionOrNull()!!)
            return Result.success(resultNome.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverNomeVigia",
                    cause = e
                )
            )
        }
    }

}