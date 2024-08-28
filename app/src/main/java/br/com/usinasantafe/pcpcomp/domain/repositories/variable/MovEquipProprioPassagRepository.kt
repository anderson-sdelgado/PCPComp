package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovEquipProprioPassagRepository {
    suspend fun add(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun clear(): Result<Boolean>
    suspend fun list(): Result<List<Int>>
    suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun save(
        id: Int
    ): Result<Boolean>
}