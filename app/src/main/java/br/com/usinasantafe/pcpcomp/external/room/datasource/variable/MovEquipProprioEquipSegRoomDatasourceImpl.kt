package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel

class MovEquipProprioEquipSegRoomDatasourceImpl(
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao
): MovEquipProprioEquipSegRoomDatasource {

    override suspend fun addAll(list: List<MovEquipProprioEquipSegRoomModel>): Result<Boolean> {
        try {
            movEquipProprioEquipSegDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                DatasourceException(
                    function = "MovEquipProprioEquipSegRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

}