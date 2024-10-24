package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface SetStatusOutsideMovResidencia {    suspend operator fun invoke(
    id: Int
): Result<Boolean>
}

class SetStatusOutsideMovResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): SetStatusOutsideMovResidencia {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        try {
            val resultGet = movEquipResidenciaRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movEquipResidencia = resultGet.getOrNull()!!
            val resultClose = movEquipResidenciaRepository.setOutside(movEquipResidencia)
            if (resultClose.isFailure)
                return Result.failure(resultClose.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CloseAllMovResidencia",
                    cause = e
                )
            )
        }
    }

}