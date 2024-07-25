package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.webservice.stable.ColabRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toColab
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toColabModel

class ColabRepositoryImpl(
    private val colabRoomDatasource: ColabRoomDatasource,
    private val colabRetrofitDatasource: ColabRetrofitDatasource
): ColabRepository {

    override suspend fun addAll(list: List<Colab>): Result<Boolean> {
        try {
            val colabModelList = list.map { it.toColabModel() }
            return colabRoomDatasource.addAll(colabModelList)
        } catch (e: Exception){
            return Result.failure(RepositoryException(cause = e))
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return colabRoomDatasource.deleteAll()
    }

    override suspend fun recoverAll(token: String): Result<List<Colab>> {
        try {
            val recoverAll = colabRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            val result = recoverAll.getOrNull()!!
            val colabModelList = result.map { it.toColab() }
            return Result.success(colabModelList)
        } catch (e: Exception) {
            return Result.failure(RepositoryException(cause = e))
        }
    }

}