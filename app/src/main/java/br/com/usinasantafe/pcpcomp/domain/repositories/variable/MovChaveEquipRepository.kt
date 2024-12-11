package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChaveEquip

interface MovChaveEquipRepository {
    suspend fun listRemove(): Result<List<MovChaveEquip>>
}