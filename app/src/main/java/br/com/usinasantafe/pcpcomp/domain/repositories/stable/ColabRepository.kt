package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab

interface ColabRepository {
    suspend fun addAll(list: List<Colab>): Result<Boolean>
    suspend fun checkMatric(matric: Long): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getNome(matric: Long): Result<String>
    suspend fun recoverAll(token: String): Result<List<Colab>>
}