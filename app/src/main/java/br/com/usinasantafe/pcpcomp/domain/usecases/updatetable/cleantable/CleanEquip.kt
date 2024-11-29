package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository

interface CleanEquip {
    suspend operator fun invoke(): Result<Boolean>
}

class ICleanEquip(
    private val equipRepository: EquipRepository
): CleanEquip {

    override suspend fun invoke(): Result<Boolean> {
        return equipRepository.deleteAll()
    }

}