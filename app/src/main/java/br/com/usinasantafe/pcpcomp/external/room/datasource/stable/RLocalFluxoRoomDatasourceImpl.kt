package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel

class RLocalFluxoRoomDatasourceImpl(
    private val rLocalFluxoDao: RLocalFluxoDao
): RLocalFluxoRoomDatasource {

    override suspend fun addAll(list: List<RLocalFluxoRoomModel>): Result<Boolean> {
        try {
            rLocalFluxoDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "RLocalFluxoRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            rLocalFluxoDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "RLocalFluxoDatasourceImpl.deleteAll",
                    cause = e
                )
            )
        }
    }

}