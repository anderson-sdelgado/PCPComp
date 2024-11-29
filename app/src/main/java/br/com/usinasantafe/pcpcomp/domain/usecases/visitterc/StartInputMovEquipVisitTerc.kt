package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface StartInputMovEquipVisitTerc {
    suspend operator fun invoke(): Result<Boolean>
}

class IStartInputMovEquipVisitTerc(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipVisitTercPassagRepository: MovEquipVisitTercPassagRepository,
) : StartInputMovEquipVisitTerc {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultStart = movEquipVisitTercRepository.start()
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            val resultClear = movEquipVisitTercPassagRepository.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
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