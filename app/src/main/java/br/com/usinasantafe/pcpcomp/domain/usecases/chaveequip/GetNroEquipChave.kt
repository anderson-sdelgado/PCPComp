package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository

interface GetNroEquipChave {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetNroEquipChave(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val equipRepository: EquipRepository
): GetNroEquipChave {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultIdEquip = movChaveEquipRepository.getIdEquip(id = id)
            if (resultIdEquip.isFailure)
                return Result.failure(resultIdEquip.exceptionOrNull()!!)
            val idEquip = resultIdEquip.getOrNull()!!
            val resultEquip = equipRepository.getNro(idEquip = idEquip)
            if (resultEquip.isFailure)
                return Result.failure(resultEquip.exceptionOrNull()!!)
            return Result.success(resultEquip.getOrNull()!!.toString())
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "GetNroEquipImpl",
                    cause = e
                )
            )
        }
    }

}