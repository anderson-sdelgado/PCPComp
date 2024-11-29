package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

class IMovEquipProprioEquipSegRoomDatasource(
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao
) : MovEquipProprioEquipSegRoomDatasource {

    override suspend fun add(idEquip: Int, id: Int): Result<Boolean> {
        try {
            movEquipProprioEquipSegDao.insert(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = id,
                    idEquip = idEquip
                )
            )
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegRoomDatasourceImpl.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): Result<Boolean> {
        try {
            movEquipProprioEquipSegDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        try {
            val list = movEquipProprioEquipSegDao.get(id)
            for(mov in list) {
                movEquipProprioEquipSegDao.delete(mov)
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

    override suspend fun delete(idEquip: Int, id: Int): Result<Boolean> {
        try {
            val mov = movEquipProprioEquipSegDao.get(id, idEquip)
            movEquipProprioEquipSegDao.delete(mov)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegRoomDatasourceImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(id: Int): Result<List<MovEquipProprioEquipSegRoomModel>> {
        return try {
            Result.success(movEquipProprioEquipSegDao.list(id))
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegRoomDatasourceImpl.list",
                    cause = e
                )
            )
        }
    }

}