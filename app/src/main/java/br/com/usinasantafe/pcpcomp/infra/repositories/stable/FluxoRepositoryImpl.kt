package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Fluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.FluxoRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.FluxoRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.FluxoRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel

class FluxoRepositoryImpl(
    private val fluxoRoomDatasource: FluxoRoomDatasource,
    private val fluxoRetrofitDatasource: FluxoRetrofitDatasource
): FluxoRepository {

    override suspend fun addAll(list: List<Fluxo>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            return fluxoRoomDatasource.addAll(roomModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "FluxoRepositoryImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return fluxoRoomDatasource.deleteAll()
    }

    override suspend fun recoverAll(token: String): Result<List<Fluxo>> {
        try {
            val resultRecoverAll = fluxoRetrofitDatasource.recoverAll(token)
            if (resultRecoverAll.isFailure)
                return Result.failure(resultRecoverAll.exceptionOrNull()!!)
            val entityList = resultRecoverAll.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "FluxoRepositoryImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}