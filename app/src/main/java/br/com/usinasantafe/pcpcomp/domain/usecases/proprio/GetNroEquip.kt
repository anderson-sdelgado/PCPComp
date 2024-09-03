package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface GetNroEquip {
    suspend operator fun invoke(id: Int): Result<String>
}

class GetNroEquipImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val equipRepository: EquipRepository
): GetNroEquip {

    override suspend fun invoke(id: Int): Result<String> {
        try {
            val resultMov = movEquipProprioRepository.get(id = id)
            if (resultMov.isFailure)
                return Result.failure(resultMov.exceptionOrNull()!!)
            val idEquip = resultMov.getOrNull()!!.idEquipMovEquipProprio!!
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