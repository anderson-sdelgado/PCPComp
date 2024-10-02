package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipResidenciaSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class MovEquipResidenciaRepositoryImpl(
    private val movEquipResidenciaSharedPreferencesDatasource: MovEquipResidenciaSharedPreferencesDatasource,
    private val movEquipResidenciaRoomDatasource: MovEquipResidenciaRoomDatasource,
) : MovEquipResidenciaRepository {

    override suspend fun get(id: Int): Result<MovEquipResidencia> {
        try {
            val resultGet = movEquipResidenciaRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity()
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getMotorista(id: Int): Result<String> {
        try {
            val resultGet = movEquipResidenciaRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().motoristaMovEquipResidencia!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.getMotorista",
                    cause = e
                )
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val resultGet = movEquipResidenciaRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().observMovEquipResidencia
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.getObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun getPlaca(id: Int): Result<String> {
        try {
            val resultGet = movEquipResidenciaRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().placaMovEquipResidencia!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.getPlaca",
                    cause = e
                )
            )
        }
    }

    override suspend fun getVeiculo(id: Int): Result<String> {
        try {
            val resultGet = movEquipResidenciaRoomDatasource.get(id)
            if (resultGet.isFailure)
                return Result.failure(resultGet.exceptionOrNull()!!)
            return Result.success(
                resultGet.getOrNull()!!.roomModelToEntity().veiculoMovEquipResidencia!!
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.getVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovEquipResidencia>> {
        try {
            val resultListOpen = movEquipResidenciaRoomDatasource.listOpen()
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
                    function = "MovEquipResidenciaRepositoryImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInputOpen(): Result<List<MovEquipResidencia>> {
        try {
            val resultListOpen = movEquipResidenciaRoomDatasource.listInputOpen()
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
                    function = "MovEquipResidenciaRepositoryImpl.listOpen",
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
            val resultGetMov = movEquipResidenciaSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val movEquipResidenciaRoomModel =
                resultGetMov.getOrNull()!!.sharedPreferencesModelToEntity()
                    .entityToRoomModel(
                        matricVigia = matricVigia,
                        idLocal = idLocal
                    )
            val resultSave = movEquipResidenciaRoomDatasource.save(movEquipResidenciaRoomModel)
            if(resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return Result.failure(
                    RepositoryException(
                        function = "MovEquipResidenciaRepositoryImpl.save",
                        cause = Exception("Id is 0")
                    )
                )
            }
            val resultClear = movEquipResidenciaSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipResidencia: MovEquipResidencia): Result<Boolean> {
        try {
            val movEquipResidenciaRoomModel = movEquipResidencia.entityToRoomModel(
                matricVigia = movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                idLocal = movEquipResidencia.idLocalMovEquipResidencia!!
            )
            val resultSetClose =
                movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)
            if (resultSetClose.isFailure)
                return Result.failure(resultSetClose.exceptionOrNull()!!)
            return Result.success(resultSetClose.getOrNull()!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.setClose",
                    cause = e
                )
            )
        }
    }

    override suspend fun setMotorista(
        motorista: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setMotorista(motorista)
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setMotorista(motorista, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.setMotorista",
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
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setObserv(observ, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.setObserv",
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
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setPlaca(placa)
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setPlaca(placa, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.setPlaca",
                    cause = e
                )
            )
        }
    }

    override suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipResidenciaSharedPreferencesDatasource.setVeiculo(veiculo)
                FlowApp.CHANGE -> movEquipResidenciaRoomDatasource.setVeiculo(veiculo, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.setVeiculo",
                    cause = e
                )
            )
        }
    }

    override suspend fun start(): Result<Boolean> {
        return movEquipResidenciaSharedPreferencesDatasource.start()
    }

    override suspend fun start(movEquipResidencia: MovEquipResidencia): Result<Boolean> {
        try {
            val movEquipResidenciaSharedPreferencesModel =
                movEquipResidencia.entityToSharedPreferencesModel()
            return movEquipResidenciaSharedPreferencesDatasource.start(
                movEquipResidenciaSharedPreferencesModel
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipResidenciaRepositoryImpl.start",
                    cause = e
                )
            )
        }
    }

}