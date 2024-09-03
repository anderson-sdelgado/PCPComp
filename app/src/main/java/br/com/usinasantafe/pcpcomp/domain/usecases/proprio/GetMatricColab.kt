package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface GetMatricColab {
    suspend operator fun invoke(id: Int): Result<String>
}

class GetMatricColabImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository
) : GetMatricColab {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultMov = movEquipProprioRepository.get(id = id)
            if (resultMov.isFailure)
                return Result.failure(resultMov.exceptionOrNull()!!)
            val matricColab = resultMov.getOrNull()!!.matricColabMovEquipProprio!!
            return Result.success(matricColab.toString())
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetMatricColabImpl",
                    cause = e
                )
            )
        }
    }

}