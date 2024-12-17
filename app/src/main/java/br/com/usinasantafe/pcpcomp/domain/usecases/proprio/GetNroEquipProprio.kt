package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface GetNroEquipProprio {
    suspend operator fun invoke(id: Int): Result<String>
}

class IGetNroEquipProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val equipRepository: EquipRepository
): GetNroEquipProprio {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultIdEquip = movEquipProprioRepository.getIdEquip(id = id)
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