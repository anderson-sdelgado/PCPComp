package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository

interface StartMovEquipResidencia {
    suspend operator fun invoke(movEquipResidencia: MovEquipResidencia? = null): Result<Boolean>
}

class StartMovEquipResidenciaImpl(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): StartMovEquipResidencia {

    override suspend fun invoke(movEquipResidencia: MovEquipResidencia?): Result<Boolean> {
        try {
            if (movEquipResidencia == null) {
                val resultStart = movEquipResidenciaRepository.start()
                if (resultStart.isFailure)
                    return Result.failure(resultStart.exceptionOrNull()!!)
                return Result.success(true)
            }
            val resultStart = movEquipResidenciaRepository.start(movEquipResidencia)
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