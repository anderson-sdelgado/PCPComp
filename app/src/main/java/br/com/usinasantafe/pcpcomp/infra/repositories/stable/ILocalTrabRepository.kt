package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.LocalTrab
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.LocalTrabRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.LocalTrabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.LocalTrabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel

class ILocalTrabRepository(
    private val localTrabRoomDatasource: LocalTrabRoomDatasource,
    private val localTrabRetrofitDatasource: LocalTrabRetrofitDatasource
): LocalTrabRepository {

    override suspend fun addAll(list: List<LocalTrab>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            return localTrabRoomDatasource.addAll(roomModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "ILocalTrabRepository.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return localTrabRoomDatasource.deleteAll()
    }

    override suspend fun getDescr(id: Int): Result<String> {
        return localTrabRoomDatasource.getDescr(id)
    }

    override suspend fun recoverAll(token: String): Result<List<LocalTrab>> {
        try {
            val resultRecoverAll = localTrabRetrofitDatasource.recoverAll(token)
            if (resultRecoverAll.isFailure)
                return Result.failure(resultRecoverAll.exceptionOrNull()!!)
            val entityList = resultRecoverAll.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "ILocalTrabRepository.recoverAll",
                    cause = e
                )
            )
        }
    }

}