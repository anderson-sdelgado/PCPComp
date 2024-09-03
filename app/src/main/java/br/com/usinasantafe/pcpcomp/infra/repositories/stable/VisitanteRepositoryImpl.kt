package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toVisitanteModel

class VisitanteRepositoryImpl(
    private val visitanteRoomDatasource: VisitanteRoomDatasource,
    private val visitanteRetrofitDatasource: VisitanteRetrofitDatasource
): VisitanteRepository {
    
    override suspend fun addAll(list: List<Visitante>): Result<Boolean> {
        try {
            val visitanteModelList = list.map { it.toVisitanteModel() }
            return visitanteRoomDatasource.addAll(visitanteModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return visitanteRoomDatasource.deleteAll()
    }

    override suspend fun recoverAll(token: String): Result<List<Visitante>> {
        try {
            val recoverAll = visitanteRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}