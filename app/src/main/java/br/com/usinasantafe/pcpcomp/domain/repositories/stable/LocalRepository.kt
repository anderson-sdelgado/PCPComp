package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local


interface LocalRepository {
    suspend fun addAll(list: List<Local>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getDescr(id: Int): Result<String>
    suspend fun list(): Result<List<Local>>
    suspend fun recoverAll(token: String): Result<List<Local>>
}