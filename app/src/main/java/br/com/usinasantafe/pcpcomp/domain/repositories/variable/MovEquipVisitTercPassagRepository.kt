package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovEquipVisitTercPassagRepository {
    suspend fun add(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun clear(): Result<Boolean>

    suspend fun delete(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipVisitTercPassag>>

    suspend fun save(
        id: Int
    ): Result<Boolean>
}