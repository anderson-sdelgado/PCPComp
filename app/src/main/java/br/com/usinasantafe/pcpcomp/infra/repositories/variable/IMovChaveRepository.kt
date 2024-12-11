package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovChave
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovChaveRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovChaveRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovChaveSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class IMovChaveRepository(
    private val movChaveRoomDatasource: MovChaveRoomDatasource,
    private val movChaveSharedPreferencesDatasource: MovChaveSharedPreferencesDatasource
): MovChaveRepository {

    override suspend fun listRemove(): Result<List<MovChave>> {
        try {
            val resultListRemove = movChaveRoomDatasource.listRemove()
            if(resultListRemove.isFailure){
                return Result.failure(
                    resultListRemove.exceptionOrNull()!!
                )
            }
            val entityList = resultListRemove.getOrNull()!!.map {
                it.roomModelToEntity()
            }
            return Result.success(entityList)
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovChaveRepositoryImpl.listRemove",
                    cause = e
                )
            )
        }
    }

    override suspend fun setIdChave(
        idChave: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movChaveSharedPreferencesDatasource.setIdChave(idChave)
                FlowApp.CHANGE -> TODO()
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovChaveRepositoryImpl.setIdChave",
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
                FlowApp.CHANGE -> TODO()
            }
        } catch (e: Exception) {
            Result.failure(
                RepositoryException(
                    function = "MovChaveRepositoryImpl.setMatricColab",
                    cause = e
                )
            )
        }
    }


    override suspend fun start(): Result<Boolean> {
        return movChaveSharedPreferencesDatasource.start()
    }

}