package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository

interface CloseAllMovChaveEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): CloseAllMovChaveEquip {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = movChaveEquipRepository.listOpen()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val entityList = resultList.getOrNull()!!
            for(entity in entityList){
                val resulClose = movChaveEquipRepository.setClose(entity.idMovChaveEquip!!)
                if(resulClose.isFailure)
                    return Result.failure(resulClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "ICloseAllMovChaveEquip",
                    cause = e
                )
            )
        }
    }

}