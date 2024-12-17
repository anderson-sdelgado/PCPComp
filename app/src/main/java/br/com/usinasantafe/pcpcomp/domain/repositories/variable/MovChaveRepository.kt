package br.com.usinasantafe.pcpcomp.domain.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.utils.FlowApp

interface MovChaveRepository {
    suspend fun get(id: Int): Result<MovChave>
    suspend fun getMatricColab(id: Int): Result<Int>
    suspend fun getObserv(id: Int): Result<String?>
    suspend fun listInside(): Result<List<MovChave>>
    suspend fun listOpen(): Result<List<MovChave>>
    suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int>
    suspend fun setClose(id: Int): Result<Boolean>
    suspend fun setIdChave(
        idChave: Int,
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
    suspend fun start(movChave: MovChave): Result<Boolean>
}