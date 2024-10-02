package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface StartMovEquipVisitTerc {
    suspend operator fun invoke(movEquipVisitTerc: MovEquipVisitTerc? = null): Result<Boolean>
}

class StartMovEquipVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
): StartMovEquipVisitTerc {

    override suspend fun invoke(movEquipVisitTerc: MovEquipVisitTerc?): Result<Boolean> {
        try {
            if(movEquipVisitTerc == null){
                val resultStart = movEquipVisitTercRepository.start()
                if(resultStart.isFailure)
                    return Result.failure(resultStart.exceptionOrNull()!!)
                val resultClear = movEquipVisitTercPassagRepository.clear()
                if(resultClear.isFailure)
                    return Result.failure(resultClear.exceptionOrNull()!!)
                return Result.success(true)
            }
            val resultStart = movEquipVisitTercRepository.start(movEquipVisitTerc)
            if(resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            val passagList = movEquipVisitTerc.movEquipVisitTercPassagList
            if (passagList != null) {
                for(passag in passagList){
                    val resultAdd = movEquipVisitTercPassagRepository.add(
                        idVisitTerc = passag.idVisitTerc!!,
                        flowApp = FlowApp.ADD,
                        id = 0
                    )
                    if(resultAdd.isFailure)
                        return Result.failure(resultAdd.exceptionOrNull()!!)
                }
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartMovEquipVisitTercImpl",
                    cause = e.cause
                )
            )
        }
    }

}