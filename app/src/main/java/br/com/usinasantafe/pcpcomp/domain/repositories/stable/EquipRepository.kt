package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip

interface EquipRepository {
    suspend fun addAll(list: List<Equip>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getNro(idEquip: Long): Result<Long>
    suspend fun recoverAll(token: String): Result<List<Equip>>
}