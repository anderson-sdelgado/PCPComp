package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante

interface VisitanteRepository {
    suspend fun addAll(list: List<Visitante>): Result<Boolean>
    suspend fun checkCPF(cpf: String): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<Visitante>
    suspend fun getCpf(id: Int): Result<String>
    suspend fun getId(cpf: String): Result<Int>
    suspend fun getNome(cpf: String): Result<String>
    suspend fun getEmpresas(cpf: String): Result<String>
    suspend fun recoverAll(token: String): Result<List<Visitante>>
}