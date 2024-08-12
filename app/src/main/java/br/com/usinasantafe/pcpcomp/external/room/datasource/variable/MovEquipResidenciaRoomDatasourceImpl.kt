package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData

class MovEquipResidenciaRoomDatasourceImpl(
    private val movEquipResidenciaDao: MovEquipResidenciaDao
): MovEquipResidenciaRoomDatasource {

    override suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>> {
        try{
            val listOpen = movEquipResidenciaDao.listStatus(StatusData.OPEN)
            return Result.success(listOpen)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean> {
        try {
            movEquipResidenciaRoomModel.statusMovEquipResidencia = StatusData.CLOSE
            movEquipResidenciaDao.update(movEquipResidenciaRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.setClose",
                    cause = e
                )
            )
        }
    }

}