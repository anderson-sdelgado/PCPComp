package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository

interface CheckMatricColab {
    suspend operator fun invoke(matricColab: String): Result<Boolean>
}

class CheckMatricColabImpl(
    private val colabRepository: ColabRepository
): CheckMatricColab {

    override suspend fun invoke(matricColab: String): Result<Boolean> {
        try {
            val matric = matricColab.toLong()
            val result = colabRepository.checkMatric(matric)
            if(result.isFailure)
                return result
            return Result.success(result.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckMatricColab",
                    cause = e
                )
            )
        }
    }

}
