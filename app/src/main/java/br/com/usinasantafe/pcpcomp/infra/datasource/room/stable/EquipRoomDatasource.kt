package br.com.usinasantafe.pcpcomp.infra.datasource.room.stable

import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel

interface EquipRoomDatasource {
    suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean>
    suspend fun checkNro(nroEquip: Long): Result<Boolean>
    suspend fun getId(nroEquip: Long): Result<Int>
    suspend fun getNro(idEquip: Int): Result<Long>
    suspend fun deleteAll(): Result<Boolean>
}