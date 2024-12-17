package br.com.usinasantafe.pcpcomp.domain.usecases.chaveequip

import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveEquipRepository

interface GetObservMovChaveEquip {
    suspend operator fun invoke(id: Int): Result<String?>
}

class IGetObservMovChaveEquip(
    private val movChaveEquipRepository: MovChaveEquipRepository
): GetObservMovChaveEquip {

    override suspend fun invoke(id: Int): Result<String?> {
        return movChaveEquipRepository.getObserv(id)
    }

}