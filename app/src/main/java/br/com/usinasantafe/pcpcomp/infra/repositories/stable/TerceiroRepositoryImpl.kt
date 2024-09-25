package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.toTerceiroModel

class TerceiroRepositoryImpl(
    private val terceiroRoomDatasource: TerceiroRoomDatasource,
    private val terceiroRetrofitDatasource: TerceiroRetrofitDatasource
): TerceiroRepository {

    override suspend fun addAll(list: List<Terceiro>): Result<Boolean> {
        try {
            val terceiroModelList = list.map { it.toTerceiroModel() }
            return terceiroRoomDatasource.addAll(terceiroModelList)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.addAll",
                    cause = e
                )
            )
        }
    }

    override suspend fun checkCPF(cpf: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return terceiroRoomDatasource.deleteAll()
    }

    override suspend fun get(id: Int): Result<Terceiro> {
        TODO("Not yet implemented")
    }

    override suspend fun getCpf(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getId(cpf: String): Result<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getNome(cpf: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getEmpresas(cpf: String): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun recoverAll(token: String): Result<List<Terceiro>> {
        try {
            val recoverAll = terceiroRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            return Result.success(recoverAll.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.recoverAll",
                    cause = e
                )
            )
        }
    }

}