package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toTerceiroModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toTerceiro

class TerceiroRepositoryImpl(
    private val terceiroRoomDatasource: TerceiroRoomDatasource,
    private val terceiroRetrofitDatasource: TerceiroRetrofitDatasource
): TerceiroRepository {

    override suspend fun addAll(list: List<Terceiro>): Result<Boolean> {
        try {
            val terceiroModelList = list.map { it.toTerceiroModel() }
            return terceiroRoomDatasource.addAll(terceiroModelList)
        } catch (e: Exception){
            return Result.failure(RepositoryException(cause = e))
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return terceiroRoomDatasource.deleteAll()
    }

    override suspend fun recoverAll(token: String): Result<List<Terceiro>> {
        try {
            val recoverAll = terceiroRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            val result = recoverAll.getOrNull()!!
            val terceiroModelList = result.map { it.toTerceiro() }
            return Result.success(terceiroModelList)
        } catch (e: Exception) {
            return Result.failure(RepositoryException(cause = e))
        }
    }

}