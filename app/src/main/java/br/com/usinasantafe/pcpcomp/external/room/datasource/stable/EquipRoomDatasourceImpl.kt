package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel

class EquipRoomDatasourceImpl(
    private val equipDao: EquipDao
): EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean> {
        try {
            equipDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(DatasourceException(cause = e))
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            equipDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(DatasourceException(cause = e))
        }
    }

}