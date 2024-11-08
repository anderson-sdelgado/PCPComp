package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository

interface SaveAllEquip {
    suspend operator fun invoke(list: List<Equip>): Result<Boolean>
}

class SaveAllEquipImpl(
    private val equipRepository: EquipRepository,
): SaveAllEquip {

    override suspend fun invoke(list: List<Equip>): Result<Boolean> {
        return equipRepository.addAll(list)
    }

}