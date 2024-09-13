package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.retrofit.variable.MovEquipProprioRetrofitDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.entityToRetrofitModelOutput
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.retrofitModelInputToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

class MovEquipProprioRepositoryImpl(
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource,
    private val movEquipProprioRoomDatasource: MovEquipProprioRoomDatasource,
    private val movEquipProprioRetrofitDatasource: MovEquipProprioRetrofitDatasource,
) : MovEquipProprioRepository {

    override suspend fun checkSend(): Result<Boolean> {
        return movEquipProprioRoomDatasource.checkSend()
    }

    override suspend fun get(id: Int): Result<MovEquipProprio> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getDestino(id: Int): Result<String> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity().destinoMovEquipProprio!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getTipoMov(): Result<TypeMov> {
        try {
            val resultGet = movEquipProprioSharedPreferencesDatasource.get()
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            val result = resultGet.getOrNull()!!.tipoMovEquipProprio
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.getTipoMov",
                    cause = e
                )
            )
        }
    }

    override suspend fun getIdEquip(id: Int): Result<Int> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity().idEquipMovEquipProprio!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity().observMovEquipProprio)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity().matricColabMovEquipProprio!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getNotaFiscal(id: Int): Result<Int?> {
        try {
            val resultGet = movEquipProprioRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity().notaFiscalMovEquipProprio)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipProprio>> {
        try {
            val resultListOpen = movEquipProprioRoomDatasource.listOpen()
            if (resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val result = resultListOpen.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(
                result
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listSend(): Result<List<MovEquipProprio>> {
        try {
            val resultListSend = movEquipProprioRoomDatasource.listSend()
            if (resultListSend.isFailure)
                return Result.failure(resultListSend.exceptionOrNull()!!)
            val listSend = resultListSend.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(listSend)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.listSend",
                    cause = e
                )
            )
        }
    }

    override suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int> {
        try {
            val resultGetMov = movEquipProprioSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val movEquipProprioRoomModel =
                resultGetMov.getOrNull()!!.sharedPreferencesModelToEntity()
                    .entityToRoomModel(matricVigia, idLocal)
            val resultSave = movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val resultClear = movEquipProprioSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0)
                return Result.failure(
                    RepositoryException(
                        function = "MovEquipProprioRepositoryImpl.save",
                        cause = Exception("Id is 0")
                    )
                )
            return Result.success(resultSave.getOrNull()!!.toInt())
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun send(
        list: List<MovEquipProprio>,
        number: Long,
        token: String
    ): Result<List<MovEquipProprio>> {
        val resultSend = movEquipProprioRetrofitDatasource.send(
            list = list.map { it.entityToRetrofitModelOutput(number) },
            token = token
        )
        if (resultSend.isFailure)
            return Result.failure(resultSend.exceptionOrNull()!!)
        val listInput = resultSend.getOrNull()!!
        val resultList = listInput.map {
            it.retrofitModelInputToEntity()
        }
        return Result.success(resultList)
    }

    override suspend fun setClose(movEquipProprio: MovEquipProprio): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprio.entityToRoomModel(
                matricVigia = movEquipProprio.matricVigiaMovEquipProprio!!,
                idLocal = movEquipProprio.idLocalMovEquipProprio!!
            )
            val resultSetClose = movEquipProprioRoomDatasource.setClose(movEquipProprioRoomModel)
            if (resultSetClose.isFailure)
                return Result.failure(resultSetClose.exceptionOrNull()!!)
            return Result.success(resultSetClose.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setClose",
                    cause = e
                )
            )
        }
    }

    override suspend fun setDestino(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setDestino(destino)
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setDestino(destino, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setDestino",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdEquip(
        idEquip: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setIdEquip(idEquip)
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setIdEquip(idEquip, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setIdEquip",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setMatricColab(matricColab)
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setMatricColab(matricColab, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun setNotaFiscal(
        notaFiscal: Int?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setNotaFiscal(notaFiscal)
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setNotaFiscal(notaFiscal, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setNotaFiscal",
                    cause = e
                )
            )
        }
    }

    override suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movEquipProprioRoomDatasource.setObserv(observ, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSent(list: List<MovEquipProprio>): Result<Boolean> {
        try {
            for (movEquipProprio in list) {
                val resultSetSent =
                    movEquipProprioRoomDatasource.setSent(movEquipProprio.idMovEquipProprio!!)
                if (resultSetSent.isFailure)
                    return Result.failure(resultSetSent.exceptionOrNull()!!)
            }
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipProprioRepositoryImpl.setStatusSent",
                    cause = e
                )
            )
        }
    }

    override suspend fun setSend(id: Int): Result<Boolean> {
        return movEquipProprioRoomDatasource.setSend(id)
    }

    override suspend fun start(typeMov: TypeMov): Result<Boolean> {
        return movEquipProprioSharedPreferencesDatasource.start(typeMov)
    }

}