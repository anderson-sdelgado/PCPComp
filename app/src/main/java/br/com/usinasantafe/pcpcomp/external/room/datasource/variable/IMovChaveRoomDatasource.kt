package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner

class IMovChaveRoomDatasource(
    private val movChaveDao: MovChaveDao
): MovChaveRoomDatasource {

    override suspend fun listRemove(): Result<List<MovChaveRoomModel>> {
        return try {
            Result.success(
                movChaveDao.listStatusForeigner(
                    statusForeigner = StatusForeigner.INSIDE
                )
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "MovChaveRoomDatasourceImpl.listRemove",
                    cause = e
                )
            )
        }
    }

}