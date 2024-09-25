package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface CloseMovProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class CloseMovProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
): CloseMovProprio {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        try {
            val resultGet = movEquipProprioRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipProprio = resultGet.getOrNull()!!
            val resultClose = movEquipProprioRepository.setClose(movEquipProprio)
            if(resultClose.isFailure)
                return Result.failure(resultClose.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovProprio",
                    cause = e
                )
            )
        }
    }

}