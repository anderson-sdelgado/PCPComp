package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprioEquipSeg
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovEquipProprioEquipSegRepository {
    suspend fun add(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun clear(): Result<Boolean>
    suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipProprioEquipSeg>>

    suspend fun delete(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>

    suspend fun save(
        id: Int
    ): Result<Boolean>
}