package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository

interface SaveEquip {
    suspend operator fun invoke(list: List<Equip>): Result<Boolean>
}

class ISaveEquip(
    private val equipRepository: EquipRepository,
): SaveEquip {

    override suspend fun invoke(list: List<Equip>): Result<Boolean> {
        return equipRepository.addAll(list)
    }

}