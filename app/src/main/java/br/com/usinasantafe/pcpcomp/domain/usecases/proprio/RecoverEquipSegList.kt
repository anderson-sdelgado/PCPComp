package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository

interface RecoverEquipSegList {
    suspend operator fun invoke(): Result<List<Equip>>
}

class RecoverEquipSegListImpl(
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val equipRepository: EquipRepository
): RecoverEquipSegList {

    override suspend fun invoke(): Result<List<Equip>> {
        try {
            val resultList = movEquipProprioEquipSegRepository.list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val equipList = resultList.getOrNull()!!
            val equipSegList = equipList.map {
                val resultNroEquip = equipRepository.getNro(it)
                if(resultNroEquip.isFailure)
                    return Result.failure(resultNroEquip.exceptionOrNull()!!)
                val nroEquip = resultNroEquip.getOrNull()!!
                if(nroEquip == 0L) {
                    return Result.failure(
                        UsecaseException(
                            function = "RecoverEquipSeg",
                            cause = NullPointerException()
                        )
                    )
                }
                Equip(
                    idEquip = it,
                    nroEquip = resultNroEquip.getOrNull()!!
                )
            }
            return Result.success(equipSegList)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "RecoverEquipSeg",
                    cause = e
                )
            )
        }
    }

}