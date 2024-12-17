package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import java.util.Date

interface StartRemoveMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<Boolean>
}

class IStartRemoveMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): StartRemoveMovChaveEquip {

    override suspend fun invoke(id: Int): Result<Boolean> {
        try {
            val resultGet = movChaveEquipRepository.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val movChaveEquip = resultGet.getOrNull()!!
            movChaveEquip.observMovChaveEquip = null
            movChaveEquip.tipoMovChaveEquip = TypeMovKey.REMOVE
            movChaveEquip.dthrMovChaveEquip = Date()
            movChaveEquip.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            val resultStart = movChaveEquipRepository.start(movChaveEquip)
            if (resultStart.isFailure)
                return Result.failure(resultStart.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                UsecaseException(
                    function = "IStartRemoveMovChaveEquip",
                    cause = e.cause
                )
            )
        }
    }

}