package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface CloseAllMovProprio {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
) : CloseAllMovProprio {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultProprioList = movEquipProprioRepository.listOpen()
            if(resultProprioList.isFailure)
                return Result.failure(resultProprioList.exceptionOrNull()!!)
            val entityList = resultProprioList.getOrNull()!!
            for(entity in entityList){
                val resultClose = movEquipProprioRepository.setClose(entity.idMovEquipProprio!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovProprioOpenImpl",
                    cause = e
                )
            )
        }
    }

}