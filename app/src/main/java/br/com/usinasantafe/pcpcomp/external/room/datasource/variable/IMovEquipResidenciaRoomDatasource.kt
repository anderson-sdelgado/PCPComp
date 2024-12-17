package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend

class IMovEquipResidenciaRoomDatasource(
    private val movEquipResidenciaDao: MovEquipResidenciaDao
): MovEquipResidenciaRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movEquipResidenciaDao.listStatusData(StatusData.OPEN).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.checkOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movEquipResidenciaDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.checkSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Boolean> {
        try {
            movEquipResidenciaDao.delete(movEquipResidenciaRoomModel)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipResidenciaRoomModel> {
        return try {
            Result.success(movEquipResidenciaDao.get(id))
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipResidenciaRoomModel>> {
        try{
            val list = movEquipResidenciaDao.listStatusData(StatusData.OPEN)
            return Result.success(list)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovEquipResidenciaRoomModel>> {
        try {
            val list = movEquipResidenciaDao.listStatusForeigner(
                statusForeigner = StatusForeigner.INSIDE
            )
            return Result.success(list)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.listInputOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipResidenciaRoomModel>> {
        try {
            val list = movEquipResidenciaDao.listStatusSend(StatusSend.SEND)
            return Result.success(list)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipResidenciaRoomModel>> {
        try {
            val list = movEquipResidenciaDao.listStatusSend(StatusSend.SENT)
            return Result.success(list)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(movEquipResidenciaRoomModel: MovEquipResidenciaRoomModel): Result<Long> {
        try {
            val id = movEquipResidenciaDao.insert(movEquipResidenciaRoomModel)
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

    override suspend fun setClose(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.statusMovEquipResidencia = StatusData.CLOSE
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.setOutside",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMotorista(
        motorista: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.motoristaMovEquipResidencia = motorista
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setMotorista",
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
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.observMovEquipResidencia = observ
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.statusMovEquipForeignerResidencia = StatusForeigner.OUTSIDE
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.setOutside",
                    cause = e
                )
            )
        }
    }

    override suspend fun setPlaca(
        placa: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.placaMovEquipResidencia = placa
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.setPlaca",
                    cause = e
                )
            )
        }
    }

    override suspend fun setVeiculo(
        veiculo: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.veiculoMovEquipResidencia = veiculo
            roomModel.statusSendMovEquipResidencia= StatusSend.SEND
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.setVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipResidenciaDao.get(id)
            roomModel.statusSendMovEquipResidencia= StatusSend.SENT
            movEquipResidenciaDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipResidenciaRoomDatasourceImpl.setSent",
                    cause = e
                )
            )
        }
    }

}