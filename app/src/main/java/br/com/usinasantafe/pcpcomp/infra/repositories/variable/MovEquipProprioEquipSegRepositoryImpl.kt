package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioEquipSegRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioEquipSegRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class MovEquipProprioEquipSegRepositoryImpl(
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource,
    private val movEquipProprioEquipSegRoomDatasource: MovEquipProprioEquipSegRoomDatasource
) : MovEquipProprioEquipSegRepository {

    override suspend fun add(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.add(idEquip)
            FlowApp.CHANGE -> TODO()
        }
    }

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioEquipSegSharedPreferencesDatasource.clear()
    }

    override suspend fun list(): Result<List<Int>> {
        return movEquipProprioEquipSegSharedPreferencesDatasource.list()
    }

    override suspend fun delete(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioEquipSegSharedPreferencesDatasource.delete(idEquip)
            FlowApp.CHANGE -> TODO()
        }
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipProprioEquipSegSharedPreferencesDatasource.list()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val movEquipProprioEquipSegRoomModelList = list.map {
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = id,
                    idEquip = it
                )
            }
            val result = movEquipProprioEquipSegRoomDatasource.addAll(movEquipProprioEquipSegRoomModelList)
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioEquipSegRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }

}