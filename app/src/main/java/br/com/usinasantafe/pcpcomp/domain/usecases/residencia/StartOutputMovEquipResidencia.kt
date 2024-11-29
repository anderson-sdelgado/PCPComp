package br.com.usinasantafe.pcpcomp.domain.usecases.residencia

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import java.util.Date

interface StartOutputMovEquipResidencia {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartOutputMovEquipResidencia(
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
) : StartOutputMovEquipResidencia {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultMov = movEquipResidenciaRepository.get(id)
            if (resultMov.isFailure)
                return Result.failure(resultMov.exceptionOrNull()!!)
            val movEquipResidencia = resultMov.getOrNull()!!
            movEquipResidencia.observMovEquipResidencia = null
            movEquipResidencia.tipoMovEquipResidencia = TypeMov.OUTPUT
            movEquipResidencia.dthrMovEquipResidencia = Date()
            movEquipResidencia.statusMovEquipForeigResidencia = StatusForeigner.OUTSIDE
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