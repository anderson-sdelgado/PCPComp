package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTercPassag
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercPassagRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercPassagRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercPassagRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.modelRoomToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class IMovEquipVisitTercPassagRepository(
    private val movEquipVisitTercPassagSharedPreferencesDatasource: MovEquipVisitTercPassagSharedPreferencesDatasource,
    private val movEquipVisitTercPassagRoomDatasource: MovEquipVisitTercPassagRoomDatasource,
): MovEquipVisitTercPassagRepository {

    override suspend fun add(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when(flowApp) {
                FlowApp.ADD -> movEquipVisitTercPassagSharedPreferencesDatasource.add(idVisitTerc)
                FlowApp.CHANGE -> movEquipVisitTercPassagRoomDatasource.add(idVisitTerc, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercPassagRepositoryImpl.add",
                    cause = e
                )
            )
        }
    }

    override suspend fun clear(): Result<Boolean> {
        return movEquipVisitTercPassagSharedPreferencesDatasource.clear()
    }

    override suspend fun delete(id: Int): Result<Boolean> {
        return movEquipVisitTercPassagRoomDatasource.delete(id)
    }

    override suspend fun delete(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercPassagSharedPreferencesDatasource.delete(idVisitTerc)
                FlowApp.CHANGE -> movEquipVisitTercPassagRoomDatasource.delete(idVisitTerc, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioPassagRepositoryImpl.delete",
                    cause = e
                )
            )
        }
    }

    override suspend fun list(
        flowApp: FlowApp,
        id: Int
    ): Result<List<MovEquipVisitTercPassag>> {
        try {
            when(flowApp) {
                FlowApp.ADD -> {
                    val resultList =
                        movEquipVisitTercPassagSharedPreferencesDatasource.list()
                    if(resultList.isFailure)
                        return Result.failure(resultList.exceptionOrNull()!!)
                    val list = resultList.getOrNull()!!
                    val movEquipVisitTercPassagList = list.map {
                        MovEquipVisitTercPassag(
                            idVisitTerc = it
                        )
                    }
                    return Result.success(movEquipVisitTercPassagList)
                }
                FlowApp.CHANGE -> {
                    val resultList =
                        movEquipVisitTercPassagRoomDatasource.list(id)
                    if(resultList.isFailure)
                        return Result.failure(resultList.exceptionOrNull()!!)
                    val list = resultList.getOrNull()!!
                    val movEquipVisitTercPassagList = list.map { it.modelRoomToEntity() }
                    return Result.success(movEquipVisitTercPassagList)
                }
            }
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercPassagRepositoryImpl.list",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(id: Int): Result<Boolean> {
        try {
            val resultList = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            if(resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            val modelRoomList = list.map {
                MovEquipVisitTercPassagRoomModel(
                    idMovEquipVisitTerc = id,
                    idVisitTerc = it
                )
            }
            val result = movEquipVisitTercPassagRoomDatasource.addAll(modelRoomList)
            if(result.isFailure)
                return Result.failure(result.exceptionOrNull()!!)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercPassagRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }
}