package br.com.usinasantafe.pcpcomp.infra.repositories.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.domain.repositories.stable.TerceiroRepository
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.TerceiroRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.stable.TerceiroRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.roomModelToEntity

class ITerceiroRepository(
    private val terceiroRoomDatasource: TerceiroRoomDatasource,
    private val terceiroRetrofitDatasource: TerceiroRetrofitDatasource
): TerceiroRepository {

    override suspend fun addAll(list: List<Terceiro>): Result<Boolean> {
        try {
            val terceiroModelList = list.map { it.entityToRoomModel() }
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
        return terceiroRoomDatasource.checkCpf(cpf)
    }

    override suspend fun deleteAll(): Result<Boolean> {
        return terceiroRoomDatasource.deleteAll()
    }

    override suspend fun get(id: Int): Result<Terceiro> {
        try {
            val resultList = terceiroRoomDatasource.get(id)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            return Result.success(list[0].roomModelToEntity())
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getCpf(id: Int): Result<String> {
        try {
            val resultList = terceiroRoomDatasource.get(id)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            return Result.success(list[0].roomModelToEntity().cpfTerceiro)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.getCpf",
                    cause = e
                )
            )
        }
    }

    override suspend fun getId(cpf: String): Result<Int> {
        try {
            val resultList = terceiroRoomDatasource.get(cpf)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            return Result.success(list[0].roomModelToEntity().idBDTerceiro)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.getId",
                    cause = e
                )
            )
        }
    }

    override suspend fun getNome(cpf: String): Result<String> {
        try {
            val resultList = terceiroRoomDatasource.get(cpf)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            return Result.success(list[0].roomModelToEntity().nomeTerceiro)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.getNome",
                    cause = e
                )
            )
        }
    }

    override suspend fun getEmpresas(cpf: String): Result<String> {
        try {
            val resultList = terceiroRoomDatasource.get(cpf)
            if (resultList.isFailure)
                return Result.failure(resultList.exceptionOrNull()!!)
            val list = resultList.getOrNull()!!
            var empresas = ""
            for(roomModel in list) {
                if(empresas != ""){
                    empresas += "\n"
                }
                empresas += roomModel.roomModelToEntity().empresaTerceiro
            }
            return Result.success(empresas)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "TerceiroRepositoryImpl.getEmpresas",
                    cause = e
                )
            )
        }
    }

    override suspend fun recoverAll(token: String): Result<List<Terceiro>> {
        try {
            val recoverAll = terceiroRetrofitDatasource.recoverAll(token)
            if (recoverAll.isFailure)
                return Result.failure(recoverAll.exceptionOrNull()!!)
            val entityList = recoverAll.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
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