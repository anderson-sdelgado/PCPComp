package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro

interface TerceiroRepository {
    suspend fun addAll(list: List<Terceiro>): Result<Boolean>
    suspend fun checkCPF(cpf: String): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<Terceiro>
    suspend fun getCpf(id: Int): Result<String>
    suspend fun getId(cpf: String): Result<Int>
    suspend fun getNome(cpf: String): Result<String>
    suspend fun getEmpresas(cpf: String): Result<String>
    suspend fun recoverAll(token: String): Result<List<Terceiro>>
}