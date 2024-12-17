package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChaveEquip
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovChaveEquipRepository {
    suspend fun get(id: Int): Result<MovChaveEquip>
    suspend fun getMatricColab(id: Int): Result<Int>
    suspend fun getIdEquip(id: Int): Result<Int>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun listInside(): Result<List<MovChaveEquip>>
    suspend fun listOpen(): Result<List<MovChaveEquip>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setIdEquip(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean>
    suspend fun setOutside(
        id: Int
    ): Result<Boolean>
    suspend fun start(): Result<Boolean>
    suspend fun start(movChaveEquip: MovChaveEquip): Result<Boolean>
}