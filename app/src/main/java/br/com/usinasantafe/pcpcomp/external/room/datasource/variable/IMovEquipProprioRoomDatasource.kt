package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend

class IMovEquipProprioRoomDatasource(
    private val movEquipProprioDao: MovEquipProprioDao
): MovEquipProprioRoomDatasource {

    override suspend fun checkOpen(): Result<Boolean> {
        return try {
            Result.success(
                movEquipProprioDao.listStatusData(StatusData.OPEN).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.checkOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkSend(): Result<Boolean> {
        return try {
            Result.success(
                movEquipProprioDao.listStatusSend(StatusSend.SEND).isNotEmpty()
            )
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.checkSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(movEquipProprioRoomModel: MovEquipProprioRoomModel): Result<Boolean> {
        try {
            movEquipProprioDao.delete(movEquipProprioRoomModel)
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

    override suspend fun get(id: Int): Result<MovEquipProprioRoomModel> {
        return try {
            Result.success(movEquipProprioDao.get(id))
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipProprioRoomModel>> {
        try {
            val list = movEquipProprioDao.listStatusData(StatusData.OPEN)
            return Result.success(list)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipProprioRoomModel>> {
        try {
            val list = movEquipProprioDao.listStatusSend(StatusSend.SEND)
            return Result.success(list)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSent(): Result<List<MovEquipProprioRoomModel>> {
        try {
            val list = movEquipProprioDao.listStatusSend(StatusSend.SENT)
            return Result.success(list)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.listSend",
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

    override suspend fun setDestino(
        destino: String,
        id: Int
    ): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.destinoMovEquipProprio = destino
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setDestino",
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
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.idEquipMovEquipProprio = idEquip
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setIdEquip",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMatricColab(
        matricColab: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.matricColabMovEquipProprio = matricColab
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setIdEquip",
                    cause = e
                )
            )
        }
    }

    override suspend fun setNotaFiscal(
        notaFiscal: Int?,
        id: Int
    ): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.notaFiscalMovEquipProprio = notaFiscal
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setIdEquip",
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
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.observMovEquipProprio = observ
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setIdEquip",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSent(id: Int): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SENT
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setSent",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprioDao.get(id)
            movEquipProprioRoomModel.statusSendMovEquipProprio = StatusSend.SEND
            movEquipProprioDao.update(movEquipProprioRoomModel)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioRoomDatasourceImpl.setSend",
                    cause = e
                )
            )
        }
    }

}