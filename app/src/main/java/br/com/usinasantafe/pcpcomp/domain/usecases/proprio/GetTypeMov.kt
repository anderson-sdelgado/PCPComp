package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip

interface GetTypeMov {
    suspend operator fun invoke(): Result<TypeMovEquip>
}

class IGetTypeMov(
    private val movEquipProprioRepository: MovEquipProprioRepository,
) : GetTypeMov {

    override suspend fun invoke(): Result<TypeMovEquip> {
        return movEquipProprioRepository.getTipoMov()
    }

}