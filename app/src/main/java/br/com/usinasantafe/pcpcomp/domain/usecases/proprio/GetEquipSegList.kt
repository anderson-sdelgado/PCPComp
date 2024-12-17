package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface GetEquipSegList {
    suspend operator fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Equip>>
}

class IGetEquipSegList(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val equipRepository: EquipRepository
) : GetEquipSegList {

    override suspend fun invoke(
        flowApp: FlowApp,
        id: Int
    ): Result<List<Equip>> {
        try {
            val resultList = movEquipProprioEquipSegRepository.list(flowApp, id)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val equipList = resultList.getOrNull()!!
            val equipSegList = equipList.map {
                val resultNroEquip = equipRepository.get(it.idEquip!!)
                if (resultNroEquip.isFailure)
                    return Result.failure(resultNroEquip.exceptionOrNull()!!)
                return@map resultNroEquip.getOrNull()!!
            }
            return Result.success(equipSegList)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "RecoverEquipSeg",
                    cause = e
                )
            )
        }
    }

}