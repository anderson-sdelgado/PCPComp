package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioPassag
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovEquipProprioPassagRepository {
    suspend fun add(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun clear(): Result<Boolean>
    suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipProprioPassag>>

    suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun save(
        id: Int
    ): Result<Boolean>
}