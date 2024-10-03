package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface OutsideMovVisitTerc {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class OutsideMovVisitTercImpl(
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
) : OutsideMovVisitTerc {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        try {
            val resultGet = movEquipVisitTercRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipVisitTerc = resultGet.getOrNull()!!
            val resultClose = movEquipVisitTercRepository.setOutside(movEquipVisitTerc)
            if(resultClose.isFailure)
                return Result.failure(resultClose.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovVisitTerc",
                    cause = e
                )
            )
        }
    }

}