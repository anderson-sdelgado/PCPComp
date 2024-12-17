package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface CloseAllMovResidencia {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CloseAllMovResidencia {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultList = movEquipResidenciaRepository.listOpen()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val entityList = resultList.getOrNull()!!
            for(entity in entityList){
                val resultClose = movEquipResidenciaRepository.setClose(entity.idMovEquipResidencia!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovResidenciaImpl",
                    cause = e
                )
            )
        }
    }

}