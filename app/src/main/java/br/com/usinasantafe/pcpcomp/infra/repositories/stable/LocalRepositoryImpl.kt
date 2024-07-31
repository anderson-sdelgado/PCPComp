package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.LocalRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toLocalModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toLocal

class LocalRepositoryImpl(
    private val localRoomDatasource: LocalRoomDatasource,
    private val localRetrofitDatasource: LocalRetrofitDatasource
): LocalRepository {
    
    override suspend fun addAll(list: List<Local>): Result<Boolean> {
        try {
            val localModelList = list.map { it.toLocalModel() }
            return localRoomDatasource.addAll(localModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "LocalRepositoryImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return localRoomDatasource.deleteAll()
    }

    override suspend fun recoverAll(token: String): Result<List<Local>> {
        try {
            val recoverAll = localRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            val result = recoverAll.getOrNull()!!
            val localModelList = result.map { it.toLocal() }
            return Result.success(localModelList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "LocalRepositoryImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}