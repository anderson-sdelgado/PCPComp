package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel

class LocalRoomDatasourceImpl(
    private val localDao: LocalDao
): LocalRoomDatasource {

    override suspend fun addAll(list: List<LocalRoomModel>): Result<Boolean> {
        try {
            localDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(DatasourceException(cause = e))
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            localDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(DatasourceException(cause = e))
        }
    }
}