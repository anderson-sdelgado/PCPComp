package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel

class VisitanteRoomDatasourceImpl(
    private val visitanteDao: VisitanteDao
): VisitanteRoomDatasource {

    override suspend fun addAll(list: List<VisitanteRoomModel>): Result<Boolean> {
        try {
            visitanteDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(DatasourceException(cause = e))
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            visitanteDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(DatasourceException(cause = e))
        }
    }
}