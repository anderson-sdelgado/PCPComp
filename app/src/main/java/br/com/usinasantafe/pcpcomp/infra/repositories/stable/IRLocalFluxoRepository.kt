package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.RLocalFluxo
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.RLocalFluxoRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.RLocalFluxoRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.RLocalFluxoRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.roomModelToEntity

class IRLocalFluxoRepository(
    private val rLocalFluxoRoomDatasource: RLocalFluxoRoomDatasource,
    private val rLocalFluxoRetrofitDatasource: RLocalFluxoRetrofitDatasource
): RLocalFluxoRepository {

    override suspend fun addAll(list: List<RLocalFluxo>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            return rLocalFluxoRoomDatasource.addAll(roomModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "RLocalFluxoRepositoryImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return rLocalFluxoRoomDatasource.deleteAll()
    }

    override suspend fun list(idLocal: Int): Result<List<RLocalFluxo>> {
        val resultRoomList = rLocalFluxoRoomDatasource.list(idLocal)
        if (resultRoomList.isFailure)
            return Result.failure(resultRoomList.exceptionOrNull()!!)
        val entityList = resultRoomList.getOrNull()!!.map { it.roomModelToEntity() }
        return Result.success(entityList)
    }

    override suspend fun recoverAll(token: String): Result<List<RLocalFluxo>> {
        try {
            val resultRecoverAll = rLocalFluxoRetrofitDatasource.recoverAll(token)
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