package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface StartInputMovEquipResidencia {
    suspend operator fun invoke(movEquipResidencia: MovEquipResidencia? = null): Result<Boolean>
}

class StartInputMovEquipResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): StartInputMovEquipResidencia {

    override suspend fun invoke(movEquipResidencia: MovEquipResidencia?): Result<Boolean> {
        try {
            val resultStart = movEquipResidenciaRepository.start()
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartMovEquipResidenciaImpl",
                    cause = e.cause
                )
            )
        }
    }

}