package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository

interface CheckNroEquipProprio {
    suspend operator fun invoke(nroEquip: String): Result<Boolean>
}

class CheckNroEquipProprioImpl(
    private val equipRepository: EquipRepository
): CheckNroEquipProprio {

    override suspend fun invoke(nroEquip: String): Result<Boolean> {
        return try {
            equipRepository.checkNro(nroEquip.toLong())
        } catch (e: Exception) {
            Result.failure(
                UsecaseException(
                    function = "CheckEquipProprio",
                    cause = e
                )
            )
        }
    }

}