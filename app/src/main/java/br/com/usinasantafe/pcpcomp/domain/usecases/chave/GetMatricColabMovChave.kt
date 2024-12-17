package br.com.usinasantafe.pcpcomp.domain.usecases.chave

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository

interface GetMatricColabMovChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColabMovChave(
    private val movChaveRepository: MovChaveRepository
): GetMatricColabMovChave {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetMatricColab = movChaveRepository.getMatricColab(id)
            if (resultGetMatricColab.isFailure)
                return Result.failure(resultGetMatricColab.exceptionOrNull()!!)
            val matricColab = resultGetMatricColab.getOrNull()!!
            return Result.success(matricColab.toString())
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IGetMatricColabMovChaveImpl",
                    cause = e
                )
            )
        }
    }

}