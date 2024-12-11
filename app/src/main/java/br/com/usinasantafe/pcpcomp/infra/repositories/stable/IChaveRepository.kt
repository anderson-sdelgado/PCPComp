package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Chave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ChaveRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.ChaveRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.roomModelToEntity

class IChaveRepository(
    private val chaveRoomDatasource: ChaveRoomDatasource,
    private val chaveRetrofitDatasource: ChaveRetrofitDatasource
): ChaveRepository {

    override suspend fun addAll(list: List<Chave>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            return chaveRoomDatasource.addAll(roomModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "IChaveRepository.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return chaveRoomDatasource.deleteAll()
    }

    override suspend fun get(id: Int): Result<Chave> {
        return try {
            chaveRoomDatasource.get(id).map { it.roomModelToEntity() }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IChaveRepository.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listAll(): Result<List<Chave>> {
        try {
            val resultRoomList = chaveRoomDatasource.listAll()
            if (resultRoomList.isFailure){
                return Result.failure(resultRoomList.exceptionOrNull()!!)
            }
            val entityList = resultRoomList.getOrNull()!!.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IChaveRepository.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Chave>> {
        try {
            val resultRecoverAll = chaveRetrofitDatasource.recoverAll(token)
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