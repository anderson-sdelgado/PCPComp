package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel

class EquipRepositoryImpl(
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipRetrofitDatasource: EquipRetrofitDatasource
): EquipRepository {
    
    override suspend fun addAll(list: List<Equip>): Result<Boolean> {
        try {
            val equipModelList = list.map { it.entityToRoomModel() }
            return equipRoomDatasource.addAll(equipModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "EquipRepositoryImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkNro(nroEquip: Long): Result<Boolean> {
        return equipRoomDatasource.checkNro(nroEquip)
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return equipRoomDatasource.deleteAll()
    }

    override suspend fun getId(nroEquip: Long): Result<Int> {
        try{
            val result = equipRoomDatasource.getId(nroEquip)
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            val id = result.getOrNull()!!
            if (id == 0)
                return Result.failure(
                    RepositoryException(
                        function = "EquipRepositoryImpl.getId",
                        cause = Exception("Id is 0")
                    )
                )
            return Result.success(result.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "EquipRepositoryImpl.getId",
                    cause = e
                )
            )
        }
    }

    override suspend fun getNro(idEquip: Int): Result<Long> {
        try{
            val result = equipRoomDatasource.getNro(idEquip)
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            val id = result.getOrNull()!!
            if (id == 0L)
                return Result.failure(
                    RepositoryException(
                        function = "EquipRepositoryImpl.getNro",
                        cause = Exception("Nro is 0")
                    )
                )
            return Result.success(result.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "EquipRepositoryImpl.getNro",
                    cause = e
                )
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Equip>> {
        try {
            val recoverAll =  equipRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            val entityList = recoverAll.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "EquipRepositoryImpl.recoverAll",
                    cause = e
                )
            )
        }
    }
}