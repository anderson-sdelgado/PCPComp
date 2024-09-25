package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData

class MovEquipVisitTercRoomDatasourceImpl(
    private val movEquipVisitTercDao: MovEquipVisitTercDao
): MovEquipVisitTercRoomDatasource {

    override suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel> {
        TODO("Not yet implemented")
    }

    override suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>> {
        try{
            val listOpen = movEquipVisitTercDao.listStatus(StatusData.OPEN)
            return Result.success(listOpen)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean> {
        try {
            movEquipVisitTercRoomModel.statusMovEquipVisitTerc = StatusData.CLOSE
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setClose",
                    cause = e
                )
            )
        }
    }

}