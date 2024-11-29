package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository

interface SetStatusSentMovProprio {
    suspend operator fun invoke(list: List<MovEquipProprio>): Result<Boolean>
}

class ISetStatusSentMovProprio(
    private val movEquipProprioRepository: MovEquipProprioRepository
): SetStatusSentMovProprio {

    override suspend fun invoke(list: List<MovEquipProprio>): Result<Boolean> {
        return movEquipProprioRepository.setSent(list)
    }

}