package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository

interface CheckNroEquip {
    suspend operator fun invoke(nroEquip: String): Result<Boolean>
}

class ICheckNroEquip(
    private val equipRepository: EquipRepository
): CheckNroEquip {

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