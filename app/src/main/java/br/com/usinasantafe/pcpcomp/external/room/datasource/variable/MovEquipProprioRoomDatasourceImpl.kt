package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData

class MovEquipProprioRoomDatasourceImpl(
    private val movEquipProprioDao: MovEquipProprioDao
): MovEquipProprioRoomDatasource {

    override suspend fun listOpen(): Result<List<MovEquipProprioRoomModel>> {
        try{
            val listOpen = movEquipProprioDao.listStatus(StatusData.OPEN)
            return Result.success(listOpen)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Long> {
        try {
            val id = movEquipProprioDao.insert(movEquipProprioRoomModel)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Boolean> {
        try {
            movEquipProprioRoomModel.statusMovEquipProprio = StatusData.CLOSE
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setClose",
                    cause = e
                )
            )
        }
    }

}