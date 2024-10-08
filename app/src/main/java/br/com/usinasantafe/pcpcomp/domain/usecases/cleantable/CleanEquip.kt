package br.com.usinasantafe.pcpcomp.domain.usecases.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository

interface CleanEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class CleanEquipImpl(
    private val equipRepository: EquipRepository
): CleanEquip {

    override suspend fun invoke(): Result<Boolean> {
        return equipRepository.deleteAll()
    }

}