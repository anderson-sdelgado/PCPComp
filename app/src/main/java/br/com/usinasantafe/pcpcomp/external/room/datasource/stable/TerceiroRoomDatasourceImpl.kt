package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel

class TerceiroRoomDatasourceImpl(
    private val terceiroDao: TerceiroDao
): TerceiroRoomDatasource {

    override suspend fun addAll(list: List<TerceiroRoomModel>): Result<Boolean> {
        try {
            terceiroDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "TerceiroRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            terceiroDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "TerceiroRoomDatasourceImpl.deleteAll",
                    cause = e
                )
            )
        }
    }
}