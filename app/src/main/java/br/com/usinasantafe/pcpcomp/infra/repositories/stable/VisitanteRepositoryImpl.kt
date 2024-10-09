package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.VisitanteRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.VisitanteRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.VisitanteRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel

class VisitanteRepositoryImpl(
    private val visitanteRoomDatasource: VisitanteRoomDatasource,
    private val visitanteRetrofitDatasource: VisitanteRetrofitDatasource
): VisitanteRepository {
    
    override suspend fun addAll(list: List<Visitante>): Result<Boolean> {
        try {
            val visitanteModelList = list.map { it.entityToRoomModel() }
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

    override suspend fun checkCPF(cpf: String): Result<Boolean> {
        return visitanteRoomDatasource.checkCpf(cpf)
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return visitanteRoomDatasource.deleteAll()
    }

    override suspend fun get(id: Int): Result<Visitante> {
        try {
            val resultRoomModel = visitanteRoomDatasource.get(id)
            if (resultRoomModel.isFailure)
                return Result.failure(resultRoomModel.exceptionOrNull()!!)
            return Result.success(resultRoomModel.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getCpf(id: Int): Result<String> {
        try {
            val resultRoomModel = visitanteRoomDatasource.get(id)
            if (resultRoomModel.isFailure)
                return Result.failure(resultRoomModel.exceptionOrNull()!!)
            return Result.success(resultRoomModel.getOrNull()!!.roomModelToEntity().cpfVisitante)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.getCpf",
                    cause = e
                )
            )
        }
    }

    override suspend fun getId(cpf: String): Result<Int> {
        try {
            val resultRoomModel = visitanteRoomDatasource.get(cpf)
            if (resultRoomModel.isFailure)
                return Result.failure(resultRoomModel.exceptionOrNull()!!)
            return Result.success(resultRoomModel.getOrNull()!!.roomModelToEntity().idVisitante)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.getId",
                    cause = e
                )
            )
        }
    }

    override suspend fun getNome(cpf: String): Result<String> {
        try {
            val resultRoomModel = visitanteRoomDatasource.get(cpf)
            if (resultRoomModel.isFailure)
                return Result.failure(resultRoomModel.exceptionOrNull()!!)
            return Result.success(resultRoomModel.getOrNull()!!.roomModelToEntity().nomeVisitante)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.getNome",
                    cause = e
                )
            )
        }
    }

    override suspend fun getEmpresas(cpf: String): Result<String> {
        try {
            val resultRoomModel = visitanteRoomDatasource.get(cpf)
            if (resultRoomModel.isFailure)
                return Result.failure(resultRoomModel.exceptionOrNull()!!)
            return Result.success(resultRoomModel.getOrNull()!!.roomModelToEntity().empresaVisitante)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "VisitanteRepositoryImpl.getId",
                    cause = e
                )
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Visitante>> {
        try {
            val resultRecoverAll = visitanteRetrofitDatasource.recoverAll(token)
            if (resultRecoverAll.isFailure)
                return Result.failure(resultRecoverAll.exceptionOrNull()!!)
            return Result.success(resultRecoverAll.getOrNull()!!)
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