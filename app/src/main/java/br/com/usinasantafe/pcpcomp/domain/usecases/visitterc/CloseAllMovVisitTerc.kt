package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface CloseAllMovVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMovVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
): CloseAllMovVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultVisitTercList = movEquipVisitTercRepository.listOpen()
            if(resultVisitTercList.isFailure)
                return Result.failure(resultVisitTercList.exceptionOrNull()!!)
            val entityList = resultVisitTercList.getOrNull()!!
            for(entity in entityList){
                val resultClose = movEquipVisitTercRepository.setClose(entity.idMovEquipVisitTerc!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovVisitTercImpl",
                    cause = e
                )
            )
        }

    }

}