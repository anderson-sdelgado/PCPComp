package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface StartMovEquipProprio {
    suspend operator fun invoke(typeMov: TypeMov): Result<Boolean>
}

class StartMovEquipProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioEquipSegRepository: MovEquipProprioEquipSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
): StartMovEquipProprio {

    override suspend fun invoke(typeMov: TypeMov): Result<Boolean> {
        try {
            val resultStart = movEquipProprioRepository.start(typeMov)
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            val resultEquipSegClear = movEquipProprioEquipSegRepository.clear()
            if (resultEquipSegClear.isFailure)
                return Result.failure(resultEquipSegClear.exceptionOrNull()!!)
            val resultPassagClear = movEquipProprioPassagRepository.clear()
            if (resultPassagClear.isFailure)
                return Result.failure(resultPassagClear.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "StartMovEquipProprioImpl",
                    cause = e.cause
                )
            )
        }
    }

}