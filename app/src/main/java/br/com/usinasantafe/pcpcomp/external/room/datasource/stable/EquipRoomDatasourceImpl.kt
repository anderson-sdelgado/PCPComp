package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.errors.DatasourceException
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel

class EquipRoomDatasourceImpl(
    private val equipDao: EquipDao
): EquipRoomDatasource {

    override suspend fun addAll(list: List<EquipRoomModel>): Result<Boolean> {
        try {
            equipDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasourceImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkNro(nroEquip: Long): Result<Boolean> {
        try {
            val result = equipDao.checkNro(nroEquip) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasourceImpl.checkNro",
                    cause = e
                )
            )
        }
    }

    override suspend fun getId(nroEquip: Long): Result<Int> {
        return try{
            Result.success(equipDao.getId(nroEquip))
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasourceImpl.getNro",
                    cause = e
                )
            )
        }
    }

    override suspend fun getNro(idEquip: Int): Result<Long> {
        return try{
            val result = equipDao.getNro(idEquip)
            Result.success(result)
        } catch (e: Exception){
            Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasourceImpl.getNro",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            equipDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                DatasourceException(
                    function = "EquipRoomDatasourceImpl.deleteAll",
                    cause = e
                )
            )
        }
    }

}