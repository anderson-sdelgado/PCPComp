package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface RecoverNomeColab {
    suspend operator fun invoke(matric: String): Result<String>
}

class RecoverNomeColabImpl(
    private val colabRepository: ColabRepository,
) : RecoverNomeColab {

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