package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository

interface GetNomeColab {
    suspend operator fun invoke(matric: String): Result<String>
}

class IGetNomeColab(
    private val colabRepository: ColabRepository,
) : GetNomeColab {

    override suspend fun invoke(matric: String): Result<String> {
        try {
            val resultNome = colabRepository.getNome(matric.toInt())
            if (resultNome.isFailure)
                return Result.failure(resultNome.exceptionOrNull()!!)
            return Result.success(resultNome.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverNomeColab",
                    cause = e
                )
            )
        }
    }

}