package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend

class IMovEquipVisitTercRoomDatasource(
    private val movEquipVisitTercDao: MovEquipVisitTercDao
) : MovEquipVisitTercRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movEquipVisitTercDao.listStatusData(StatusData.OPEN).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.checkOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movEquipVisitTercDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.checkSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean> {
        try {
            movEquipVisitTercDao.delete(movEquipVisitTercRoomModel)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun get(id: Int): Result<MovEquipVisitTercRoomModel> {
        return try {
            Result.success(movEquipVisitTercDao.get(id))
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val list = movEquipVisitTercDao.listStatusData(StatusData.OPEN)
            return Result.success(list)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val list = movEquipVisitTercDao.listStatusForeigner(
                statusForeigner = StatusForeigner.INSIDE
            )
            return Result.success(list)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.listInside",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val listOpen = movEquipVisitTercDao.listStatusSend(StatusSend.SEND)
            return Result.success(listOpen)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipVisitTercRoomModel>> {
        try {
            val listOpen = movEquipVisitTercDao.listStatusSend(StatusSend.SENT)
            return Result.success(listOpen)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Long> {
        try {
            val id = movEquipVisitTercDao.insert(movEquipVisitTercRoomModel)
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

    override suspend fun setDestino(
        destino: String,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.destinoMovEquipVisitTerc = destino
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setDestino",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.idVisitTercMovEquipVisitTerc = idVisitTerc
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setIdVisitTerc",
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
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.observMovEquipVisitTerc = observ
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
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

    override suspend fun setOutside(movEquipVisitTercRoomModel: MovEquipVisitTercRoomModel): Result<Boolean> {
        try {
            movEquipVisitTercRoomModel.statusMovEquipForeigVisitTerc = StatusForeigner.OUTSIDE
            movEquipVisitTercDao.update(movEquipVisitTercRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setOutside",
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
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.placaMovEquipVisitTerc = placa
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setPlaca",
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
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.veiculoMovEquipVisitTerc = veiculo
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SENT
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setSent",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        try {
            val roomModel = movEquipVisitTercDao.get(id)
            roomModel.statusSendMovEquipVisitTerc = StatusSend.SEND
            movEquipVisitTercDao.update(roomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercRoomDatasourceImpl.setSend",
                    cause = e
                )
            )
        }
    }

}