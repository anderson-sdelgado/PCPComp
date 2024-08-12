package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioSegRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface StartMovEquipProprio {
    suspend operator fun invoke(typeMov: TypeMov): Result<Boolean>
}

class StartMovEquipProprioImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
    private val movEquipProprioSegRepository: MovEquipProprioSegRepository,
    private val movEquipProprioPassagRepository: MovEquipProprioPassagRepository
): StartMovEquipProprio {

    override suspend fun invoke(typeMov: TypeMov): Result<Boolean> {
        try {
            val resultStart = movEquipProprioRepository.start(typeMov)
            if (resultStart.isFailure)
                return resultStart
            val resultEquipSegClear = movEquipProprioSegRepository.clear()
            if (resultEquipSegClear.isFailure)
                return resultEquipSegClear
            val resultPassagClear = movEquipProprioPassagRepository.clear()
            if (resultPassagClear.isFailure)
                return resultPassagClear
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