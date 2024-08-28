package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel

class MovEquipProprioPassagRoomDatasourceImpl(
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao
): MovEquipProprioPassagRoomDatasource {

    override suspend fun addAll(list: List<MovEquipProprioPassagRoomModel>): Result<Boolean> {
        try {
            movEquipProprioPassagDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioPassagRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

}