package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

class MovEquipVisitTercRepositoryImpl(
    private val movEquipVisitTercSharedPreferencesDatasource: MovEquipVisitTercSharedPreferencesDatasource,
    private val movEquipVisitTercRoomDatasource: MovEquipVisitTercRoomDatasource,
) : MovEquipVisitTercRepository {

    override suspend fun get(id: Int): Result<MovEquipVisitTerc> {
        try {
            val resultGet = movEquipVisitTercRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(resultGet.getOrNull()!!.roomModelToEntity())
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getDestino(id: Int): Result<String> {
        try {
            val resultGet = movEquipVisitTercRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().destinoMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.getDestino",
                    cause = e
                )
            )
        }
    }

    override suspend fun getIdVisitTerc(id: Int): Result<Int> {
        try {
            val resultGet = movEquipVisitTercRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().idVisitTercMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.getIdVisitTerc",
                    cause = e
                )
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val resultGet = movEquipVisitTercRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().observMovEquipVisitTerc
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.getObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun getPlaca(id: Int): Result<String> {
        try {
            val resultGet = movEquipVisitTercRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().placaMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.getPlaca",
                    cause = e
                )
            )
        }
    }

    override suspend fun getTypeVisitTerc(
        flowApp: FlowApp,
        id: Int
    ): Result<TypeVisitTerc> {
        when (flowApp) {
            FlowApp.ADD -> {
                val resultGet = movEquipVisitTercSharedPreferencesDatasource.get()
                if (resultGet.isFailure)
                    return Result.failure(resultGet.exceptionOrNull()!!)
                return Result.success(resultGet.getOrNull()!!.tipoVisitTercMovEquipVisitTerc!!)
            }

            FlowApp.CHANGE -> {
                val resultGet = movEquipVisitTercRoomDatasource.get(id)
                if (resultGet.isFailure)
                    return Result.failure(resultGet.exceptionOrNull()!!)
                return Result.success(resultGet.getOrNull()!!.tipoVisitTercMovEquipVisitTerc)
            }
        }
    }

    override suspend fun getVeiculo(id: Int): Result<String> {
        try {
            val resultGet = movEquipVisitTercRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().veiculoMovEquipVisitTerc!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.getVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipVisitTerc>> {
        try {
            val resultListOpen = movEquipVisitTercRoomDatasource.listOpen()
            if (resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val result = resultListOpen.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInputOpen(): Result<List<MovEquipVisitTerc>> {
        try {
            val resultListOpen = movEquipVisitTercRoomDatasource.listInputOpen()
            if (resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val result = resultListOpen.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.listOpen",
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
            val resultGetMov =
                movEquipVisitTercSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val movEquipVisitTercRoomModel =
                resultGetMov.getOrNull()!!.sharedPreferencesModelToEntity()
                    .entityToRoomModel(matricVigia, idLocal)
            val resultSave = movEquipVisitTercRoomDatasource.save(movEquipVisitTercRoomModel)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return Result.failure(
                    RepositoryException(
                        function = "MovEquipVisitTercRepositoryImpl.save",
                        cause = Exception("Id is 0")
                    )
                )
            }
            val resultClear = movEquipVisitTercSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean> {
        try {
            val movEquipVisitTercRoomModel = movEquipVisitTerc.entityToRoomModel(
                matricVigia = movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                idLocal = movEquipVisitTerc.idLocalMovEquipVisitTerc!!
            )
            val resultSetClose =
                movEquipVisitTercRoomDatasource.setClose(movEquipVisitTercRoomModel)
            if (resultSetClose.isFailure)
                return Result.failure(resultSetClose.exceptionOrNull()!!)
            return Result.success(resultSetClose.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setClose",
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
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setDestino(destino)
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setDestino(destino, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setDestino",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setIdVisitTerc(
                    idVisitTerc
                )

                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setIdVisitTerc(idVisitTerc, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setIdVisitTerc",
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
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setObserv(observ, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setPlaca(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setPlaca(placa)
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setPlaca(placa, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setPlaca",
                    cause = e
                )
            )
        }
    }

    override suspend fun setTipo(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        return movEquipVisitTercSharedPreferencesDatasource.setTipo(typeVisitTerc)
    }

    override suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipVisitTercSharedPreferencesDatasource.setVeiculo(veiculo)
                FlowApp.CHANGE -> movEquipVisitTercRoomDatasource.setVeiculo(veiculo, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        return movEquipVisitTercSharedPreferencesDatasource.start()
    }

    override suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean> {
        try {
            val movEquipVisitTercSharedPreferencesModel =
                movEquipVisitTerc.entityToSharedPreferencesModel()
            return movEquipVisitTercSharedPreferencesDatasource.start(
                movEquipVisitTercSharedPreferencesModel
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.start",
                    cause = e
                )
            )
        }
    }


}