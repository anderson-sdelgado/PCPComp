package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.EquipRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.EquipRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toEquipModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toEquip

class EquipRepositoryImpl(
    private val equipRoomDatasource: EquipRoomDatasource,
    private val equipRetrofitDatasource: EquipRetrofitDatasource
): EquipRepository {
    
    override suspend fun addAll(list: List<Equip>): Result<Boolean> {
        try {
            val equipModelList = list.map { it.toEquipModel() }
            return equipRoomDatasource.addAll(equipModelList)
        } catch (e: Exception){
            return Result.failure(RepositoryException(cause = e))
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return equipRoomDatasource.deleteAll()
    }

    override suspend fun recoverAll(token: String): Result<List<Equip>> {
        try {
            val recoverAll =  equipRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            val result = recoverAll.getOrNull()!!
            val equipModelList = result.map { it.toEquip() }
            return Result.success(equipModelList)
        } catch (e: Exception) {
            return Result.failure(RepositoryException(cause = e))
        }
    }
}