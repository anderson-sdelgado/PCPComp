package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel

interface VisitanteRoomDatasource {
    suspend fun addAll(list: List<VisitanteRoomModel>): Result<Boolean>
    suspend fun checkCpf(cpf: String): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun get(id: Int): Result<VisitanteRoomModel>
    suspend fun get(cpf: String): Result<VisitanteRoomModel>
}