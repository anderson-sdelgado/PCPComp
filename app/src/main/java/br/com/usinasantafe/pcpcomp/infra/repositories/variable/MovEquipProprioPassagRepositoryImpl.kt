package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioPassagRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class MovEquipProprioPassagRepositoryImpl(
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource,
    private val movEquipProprioPassagRoomDatasource: MovEquipProprioPassagRoomDatasource,
): MovEquipProprioPassagRepository {

    override suspend fun add(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.add(matricColab)
            FlowApp.CHANGE -> TODO()
        }
    }

    override suspend fun clear(): Result<Boolean> {
        return movEquipProprioPassagSharedPreferencesDatasource.clear()
    }

    override suspend fun list(): Result<List<Int>> {
        return movEquipProprioPassagSharedPreferencesDatasource.list()
    }

    override suspend fun delete(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return when(flowApp){
            FlowApp.ADD -> movEquipProprioPassagSharedPreferencesDatasource.delete(matricColab)
            FlowApp.CHANGE -> TODO()
        }
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipProprioPassagSharedPreferencesDatasource.list()
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val movEquipProprioPassagRoomModelList = list.map {
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = id,
                    matricColab = it
                )
            }
            val result = movEquipProprioPassagRoomDatasource.addAll(movEquipProprioPassagRoomModelList)
            if (result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }

}