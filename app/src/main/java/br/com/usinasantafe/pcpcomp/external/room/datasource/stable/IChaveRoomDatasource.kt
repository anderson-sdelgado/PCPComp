package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel

class IChaveRoomDatasource(
    private val chaveDao: ChaveDao
): ChaveRoomDatasource {

    override suspend fun addAll(list: List<ChaveRoomModel>): Result<Boolean> {
        try {
            chaveDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IChaveRoomDatasource.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            chaveDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IChaveRoomDatasource.deleteAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(id: Int): Result<ChaveRoomModel> {
        try {
            val model = chaveDao.get(id)
            return Result.success(model)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IChaveRoomDatasource.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listAll(): Result<List<ChaveRoomModel>> {
        return try{
            Result.success(chaveDao.listAll())
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IChaveRoomDatasource.listAll",
                    cause = e
                )
            )
        }
    }
}