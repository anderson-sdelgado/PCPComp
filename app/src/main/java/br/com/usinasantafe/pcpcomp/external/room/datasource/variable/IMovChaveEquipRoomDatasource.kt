package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovChaveEquipDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovChaveEquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveEquipRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner

class IMovChaveEquipRoomDatasource(
    private val movChaveEquipDao: MovChaveEquipDao
): MovChaveEquipRoomDatasource {

    override suspend fun get(id: Int): Result<MovChaveEquipRoomModel> {
        return try {
            Result.success(
                movChaveEquipDao.get(id)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChaveEquipRoomModel>> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusForeigner(
                    statusForeigner = StatusForeigner.INSIDE
                )
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.listRemove",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChaveEquipRoomModel>> {
        return try {
            Result.success(
                movChaveEquipDao.listStatusData(
                    status = StatusData.OPEN
                )
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(movChaveEquipRoomModel: MovChaveEquipRoomModel): Result<Long> {
        return try {
            Result.success(
                movChaveEquipDao.insert(movChaveEquipRoomModel)
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.statusMovChaveEquip = StatusData.CLOSE
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setClose",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdEquip(
        idEquip: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.idEquipMovChaveEquip = idEquip
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setIdEquip",
                    cause = e
                )
            )
        }
    }

    override suspend fun setObserv(
        observ: String?,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.observMovChaveEquip = observ
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setIdChave",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMatricColab(
        matric: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.matricColabMovChaveEquip = matric
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveEquipRoomDatasource.setIdMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        try {
            val roomModel = movChaveEquipDao.get(id)
            roomModel.statusForeignerMovChaveEquip = StatusForeigner.OUTSIDE
            movChaveEquipDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "IMovChaveRoomDatasource.setOutside",
                    cause = e
                )
            )
        }
    }


}