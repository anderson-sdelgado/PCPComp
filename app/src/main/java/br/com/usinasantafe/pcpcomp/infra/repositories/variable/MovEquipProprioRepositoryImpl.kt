package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToMovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.modelRoomToMovEquipProprio
import br.com.usinasantafe.pcpcomp.utils.TypeMov

class MovEquipProprioRepositoryImpl(
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource,
    private val movEquipProprioRoomDatasource: MovEquipProprioRoomDatasource,
): MovEquipProprioRepository {

    override suspend fun start(typeMov: TypeMov): Result<Boolean> {
        return movEquipProprioSharedPreferencesDatasource.start(typeMov)
    }

    override suspend fun listOpen(): Result<List<MovEquipProprio>> {
        try {
            val resultListOpen = movEquipProprioRoomDatasource.listOpen()
            if(resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val result = resultListOpen.getOrNull()!!.map {
                it.modelRoomToMovEquipProprio()
            }
            return Result.success(
                result
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    message = "MovEquipProprioRepositoryImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipProprio: MovEquipProprio): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprio.entityToMovEquipProprioRoomModel(
                matricVigia = movEquipProprio.nroMatricVigiaMovEquipProprio!!,
                idLocal = movEquipProprio.idLocalMovEquipProprio!!
            )
            val resultSetClose = movEquipProprioRoomDatasource.setClose(movEquipProprioRoomModel)
            if(resultSetClose.isFailure)
                return Result.failure(resultSetClose.exceptionOrNull()!!)
            return Result.success(resultSetClose.getOrNull()!!)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    message = "MovEquipProprioRepositoryImpl.setClose",
                    cause = e
                )
            )
        }
    }

}