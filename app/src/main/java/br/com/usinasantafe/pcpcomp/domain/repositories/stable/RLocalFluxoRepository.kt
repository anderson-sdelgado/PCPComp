package br.com.usinasantafe.pcpcomp.domain.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo

interface RLocalFluxoRepository {
    suspend fun addAll(list: List<RLocalFluxo>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun list(idLocal: Int): Result<List<RLocalFluxo>>
    suspend fun recoverAll(token: String): Result<List<RLocalFluxo>>
}