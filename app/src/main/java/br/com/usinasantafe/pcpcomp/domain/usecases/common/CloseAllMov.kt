package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface CloseAllMov {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAllMov(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): CloseAllMov {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultProprioList = movEquipProprioRepository.listOpen()
            if(resultProprioList.isFailure)
                return Result.failure(resultProprioList.exceptionOrNull()!!)
            val movEquipProprioList = resultProprioList.getOrNull()!!
            for(movEquipProprio in movEquipProprioList){
                val resultClose = movEquipProprioRepository.setClose(movEquipProprio.idMovEquipProprio!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultVisitTercList = movEquipVisitTercRepository.listOpen()
            if(resultVisitTercList.isFailure)
                return Result.failure(resultVisitTercList.exceptionOrNull()!!)
            val movEquipVisitTercList = resultVisitTercList.getOrNull()!!
            for(movEquipVisitTerc in movEquipVisitTercList){
                val resultClose = movEquipVisitTercRepository.setClose(movEquipVisitTerc.idMovEquipVisitTerc!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            val resultResidenciaList = movEquipResidenciaRepository.listOpen()
            if(resultResidenciaList.isFailure)
                return Result.failure(resultResidenciaList.exceptionOrNull()!!)
            for(movEquipResidencia in resultResidenciaList.getOrNull()!!){
                val resultClose = movEquipResidenciaRepository.setClose(movEquipResidencia.idMovEquipResidencia!!)
                if(resultClose.isFailure)
                    return Result.failure(resultClose.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovOpenImpl",
                    cause = e
                )
            )
        }
    }

}