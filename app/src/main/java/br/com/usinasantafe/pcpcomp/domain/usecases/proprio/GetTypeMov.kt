package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.utils.TypeMov

interface GetTypeMov {
    suspend operator fun invoke(): Result<TypeMov>
}

class GetTypeMovImpl(
    private val movEquipProprioRepository: MovEquipProprioRepository,
) : GetTypeMov {

    override suspend fun invoke(): Result<TypeMov> {
        return movEquipProprioRepository.getTipoMov()
    }

}