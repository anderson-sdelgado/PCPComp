package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.FluxoDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.FluxoRoomModel

class FluxoRoomDatasourceImpl(
    private val fluxoDao: FluxoDao
) : FluxoRoomDatasource {

    override suspend fun addAll(list: List<FluxoRoomModel>): Result<Boolean> {
        try {
            fluxoDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "FluxoRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            fluxoDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "FluxoRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }
}