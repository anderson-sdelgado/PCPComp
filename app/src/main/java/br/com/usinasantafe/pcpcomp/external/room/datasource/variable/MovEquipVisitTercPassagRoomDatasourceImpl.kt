package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercPassagDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel

class MovEquipVisitTercPassagRoomDatasourceImpl(
    private val movEquipVisitTercPassagDao: MovEquipVisitTercPassagDao
) : MovEquipVisitTercPassagRoomDatasource {

    override suspend fun add(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean> {
        try {
            movEquipVisitTercPassagDao.insert(
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = id,
                    idVisitTerc = idVisitTerc
                )
            )
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagRoomDatasourceImpl.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun addAll(list: List<MovEquipVisitTercPassagRoomModel>): Result<Boolean> {
        try {
            movEquipVisitTercPassagDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun delete(
        idVisitTerc: Int,
        id: Int
    ): Result<Boolean> {
        try {
            val mov = movEquipVisitTercPassagDao.get(
                idVisitTerc = idVisitTerc,
                idMov = id
            )
            movEquipVisitTercPassagDao.delete(mov)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagRoomDatasourceImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(id: Int): Result<List<MovEquipVisitTercPassagRoomModel>> {
        return try {
            Result.success(movEquipVisitTercPassagDao.list(id))
        } catch (e: Exception) {
            Result.failure(
                DatasourceException(
                    function = "MovEquipVisitTercPassagRoomDatasourceImpl.list",
                    cause = e
                )
            )
        }
    }

}