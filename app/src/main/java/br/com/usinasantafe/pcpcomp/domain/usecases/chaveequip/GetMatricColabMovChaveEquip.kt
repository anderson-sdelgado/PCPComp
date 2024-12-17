package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository

interface GetMatricColabMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetMatricColabMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): GetMatricColabMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultGetMatricColab = movChaveEquipRepository.getMatricColab(id)
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