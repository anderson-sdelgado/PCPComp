package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.errors.UsecaseException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface CloseMovProprio {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class ICloseMovProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository,
): CloseMovProprio {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        return movEquipProprioRepository.setClose(id)
    }

}