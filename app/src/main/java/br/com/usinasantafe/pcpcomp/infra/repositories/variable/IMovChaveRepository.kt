package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class IMovChaveRepository(
    private val movChaveRoomDatasource: MovChaveRoomDatasource,
    private val movChaveSharedPreferencesDatasource: MovChaveSharedPreferencesDatasource
): MovChaveRepository {

    override suspend fun get(id: Int): Result<MovChave> {
        try {
            val resultGet = movChaveRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.get",
                    cause = e
                )
            )
        }
    }

    override suspend fun getMatricColab(id: Int): Result<Int> {
        try {
            val resultGet = movChaveRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.matricColabMovChave!!)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.getMatricColab",
                    cause = e
                )
            )
        }
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        try {
            val resultGet = movChaveRoomDatasource.get(id)
            if(resultGet.isFailure){
                return Result.failure(
                    resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!.roomModelToEntity()
            return Result.success(entity.observMovChave)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.getObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun listInside(): Result<List<MovChave>> {
        try {
            val resultList = movChaveRoomDatasource.listInside()
            if(resultList.isFailure){
                return Result.failure(
                    resultList.exceptionOrNull()!!
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.listInside",
                    cause = e
                )
            )
        }
    }

    override suspend fun listOpen(): Result<List<MovChave>> {
        try {
            val resultList = movChaveRoomDatasource.listOpen()
            if(resultList.isFailure){
                return Result.failure(
                    resultList.exceptionOrNull()!!
                )
            }
            val entityList = resultList.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.listOpen",
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
            val resultGetMov = movChaveSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val roomModel = resultGetMov.getOrNull()!!
                .entityToSharedPreferencesModel()
                .entityToRoomModel(
                    matricVigia = matricVigia,
                    idLocal = idLocal
                )
            val resultSave = movChaveRoomDatasource.save(roomModel)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val id = resultSave.getOrNull()!!.toInt()
            if (id == 0) {
                return Result.failure(
                    RepositoryException(
                        function = "IMovChaveRepository.save",
                        cause = Exception("Id is 0")
                    )
                )
            }
            val resultClear = movChaveSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.save",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(id: Int): Result<Boolean> {
        return movChaveRoomDatasource.setClose(id)
    }

    override suspend fun setIdChave(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setIdChave(idChave)
                FlowApp.CHANGE -> movChaveRoomDatasource.setIdChave(idChave, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setIdChave",
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
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setMatricColab(matricColab)
                FlowApp.CHANGE -> movChaveRoomDatasource.setMatricColab(matricColab, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setMatricColab",
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
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> movChaveRoomDatasource.setObserv(observ, id)
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.setObserv",
                    cause = e
                )
            )
        }
    }

    override suspend fun setOutside(id: Int): Result<Boolean> {
        return movChaveRoomDatasource.setOutside(id)
    }

    override suspend fun start(): Result<Boolean> {
        return movChaveSharedPreferencesDatasource.start()
    }

    override suspend fun start(movChave: MovChave): Result<Boolean> {
        try {
            val sharedPreferenceModel = movChave.entityToSharedPreferencesModel()
            return movChaveSharedPreferencesDatasource.start(sharedPreferenceModel)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "IMovChaveRepository.start",
                    cause = e
                )
            )
        }
    }

}