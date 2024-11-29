package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository

interface CheckMovOpen {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckMovOpen(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipVisitTercRepository: MovEquipVisitTercRepository,
    private val movEquipResidenciaRepository: MovEquipResidenciaRepository
): CheckMovOpen {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultCheckProprio = movEquipProprioRepository.checkOpen()
            if (resultCheckProprio.isFailure)
                return Result.failure(resultCheckProprio.exceptionOrNull()!!)
            if(resultCheckProprio.getOrNull()!!) return Result.success(true)
            val resultCheckVisitTerc = movEquipVisitTercRepository.checkOpen()
            if (resultCheckVisitTerc.isFailure)
                return Result.failure(resultCheckVisitTerc.exceptionOrNull()!!)
            if(resultCheckVisitTerc.getOrNull()!!) return Result.success(true)
            val resultCheckResidencia = movEquipResidenciaRepository.checkOpen()
            if (resultCheckResidencia.isFailure)
                return Result.failure(resultCheckResidencia.exceptionOrNull()!!)
            if(resultCheckResidencia.getOrNull()!!) return Result.success(true)
            return Result.success(false)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "CheckMovOpenImpl",
                    cause = e
                )
            )
        }
    }

}