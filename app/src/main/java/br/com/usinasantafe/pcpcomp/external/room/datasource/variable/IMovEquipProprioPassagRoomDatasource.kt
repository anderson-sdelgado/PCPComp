package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel

class IMovEquipProprioPassagRoomDatasource(
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao
) : MovEquipProprioPassagRoomDatasource {

    override suspend fun add(
        matricColab: Int,
        id: Int
    ): Result<Boolean> {
        try {
            movEquipProprioPassagDao.insert(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = id,
                    matricColab = matricColab
                )
            )
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasourceImpl.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun addAll(list: List<MovEquipProprioPassagRoomModel>): Result<Boolean> {
        try {
            movEquipProprioPassagDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val list = movEquipProprioPassagDao.get(id)
            for(mov in list) {
                movEquipProprioPassagDao.delete(mov)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasourceImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(matricColab: Int, id: Int): Result<Boolean> {
        try {
            val mov = movEquipProprioPassagDao.get(id, matricColab)
            movEquipProprioPassagDao.delete(mov)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasourceImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(id: Int): Result<List<MovEquipProprioPassagRoomModel>> {
        return try {
            Result.success(movEquipProprioPassagDao.list(id))
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasourceImpl.list",
                    cause = e
                )
            )
        }
    }

}