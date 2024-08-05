package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local


interface LocalRepository {
    suspend fun addAll(list: List<Local>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getAll(): Result<List<Local>>
    suspend fun getDescr(id: Long): Result<String>
    suspend fun recoverAll(token: String): Result<List<Local>>
}