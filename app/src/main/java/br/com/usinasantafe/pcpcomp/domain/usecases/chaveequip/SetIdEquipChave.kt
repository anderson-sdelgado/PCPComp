package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface SetIdEquipChave {
    suspend operator fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
}

class ISetIdEquipChave(
    private val movChaveEquipRepository: MovChaveEquipRepository,
    private val equipRepository: EquipRepository
): SetIdEquipChave {

    override suspend fun invoke(
        nroEquip: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        try {
            val resultId = equipRepository.getId(nroEquip.toLong())
            if (resultId.isFailure)
                return Result.failure(resultId.exceptionOrNull()!!)
            val idEquip = resultId.getOrNull()!!
            val resultSet = movChaveEquipRepository.setIdEquip(
                idEquip = idEquip,
                flowApp = flowApp,
                id = id
            )
            if (resultSet.isFailure)
                return Result.failure(resultSet.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ISetIdEquipChave",
                    cause = e
                )
            )
        }
    }

}