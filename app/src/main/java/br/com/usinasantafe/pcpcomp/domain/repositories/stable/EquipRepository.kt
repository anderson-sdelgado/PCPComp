package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip

interface EquipRepository {
    suspend fun addAll(list: List<Equip>): Result<Boolean>
    suspend fun checkNro(nroEquip: Long): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(idEquip: Int): Result<Equip>
    suspend fun getId(nroEquip: Long): Result<Int>
    suspend fun getNro(idEquip: Int): Result<Long>
    suspend fun getDescr(idEquip: Int): Result<String>
    suspend fun recoverAll(token: String): Result<List<Equip>>
}