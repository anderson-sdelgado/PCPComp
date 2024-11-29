package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.dateToDelete

interface DeleteMovSent {
    suspend operator fun invoke(): Result<Boolean>
}

class IDeleteMovSent(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository,
): DeleteMovSent {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultMovProprioList = movEquipProprioRepository.listSent()
            if(resultMovProprioList.isFailure)
                return Result.failure(resultMovProprioList.exceptionOrNull()!!)
            val movProprioList = resultMovProprioList.getOrNull()!!
            for(movProprio in movProprioList){
                if(movProprio.dthrMovEquipProprio < dateToDelete()){
                    val resultDelMovProprioPassag =
                        movEquipProprioPassagRepository.delete(movProprio.idMovEquipProprio!!)
                    if(resultDelMovProprioPassag.isFailure)
                        return Result.failure(resultDelMovProprioPassag.exceptionOrNull()!!)
                    val resultDelMovProprioEquipSeg =
                        movEquipProprioEquipSegRepository.delete(movProprio.idMovEquipProprio!!)
                    if(resultDelMovProprioEquipSeg.isFailure)
                        return Result.failure(resultDelMovProprioEquipSeg.exceptionOrNull()!!)
                    val resultDelMovProprio =
                        movEquipProprioRepository.delete(movProprio.idMovEquipProprio!!)
                    if(resultDelMovProprio.isFailure)
                        return Result.failure(resultDelMovProprio.exceptionOrNull()!!)
                }
            }
            val resultMovVisitTercList = movEquipVisitTercRepository.listSent()
            if(resultMovVisitTercList.isFailure)
                return Result.failure(resultMovVisitTercList.exceptionOrNull()!!)
            val movVisitTercList = resultMovVisitTercList.getOrNull()!!
            for(movVisitTerc in movVisitTercList){
                if(movVisitTerc.dthrMovEquipVisitTerc < dateToDelete()){
                    val resultDelMovVisitTerc = movEquipVisitTercRepository.delete(movVisitTerc.idMovEquipVisitTerc!!)
                    if(resultDelMovVisitTerc.isFailure)
                        return Result.failure(resultDelMovVisitTerc.exceptionOrNull()!!)
                    val resultDelMovVisitTercPassag = movEquipVisitTercPassagRepository.delete(movVisitTerc.idMovEquipVisitTerc!!)
                    if(resultDelMovVisitTercPassag.isFailure)
                        return Result.failure(resultDelMovVisitTercPassag.exceptionOrNull()!!)
                }
            }
            val resultMovResidenciaList = movEquipResidenciaRepository.listSent()
            if(resultMovResidenciaList.isFailure)
                return Result.failure(resultMovResidenciaList.exceptionOrNull()!!)
            val movResidenciaList = resultMovResidenciaList.getOrNull()!!
            for(movResidencia in movResidenciaList){
                if(movResidencia.dthrMovEquipResidencia < dateToDelete()){
                    val resultDelMovResidencia = movEquipResidenciaRepository.delete(movResidencia.idMovEquipResidencia!!)
                    if(resultDelMovResidencia.isFailure)
                        return Result.failure(resultDelMovResidencia.exceptionOrNull()!!)
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "DeleteMovSentImpl",
                    cause = e
                )
            )
        }
    }

}