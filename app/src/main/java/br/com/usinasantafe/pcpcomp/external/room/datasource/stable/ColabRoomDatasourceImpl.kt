package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel

class ColabRoomDatasourceImpl(
    private val colabDao: ColabDao
): ColabRoomDatasource {

    override suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean> {
        try {
            colabDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkMatric(matric: Long): Result<Boolean> {
        try {
            val result = colabDao.checkMatric(matric) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasourceImpl.checkMatric",
                    cause = e
                )
            )
        }
    }

    override suspend fun getNome(matric: Long): Result<String> {
        try {
            val nome = colabDao.getNome(matric)
            return Result.success(nome)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasourceImpl.getNome",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            colabDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "ColabRoomDatasourceImpl.deleteAll",
                    cause = e
                )
            )
        }
    }

}