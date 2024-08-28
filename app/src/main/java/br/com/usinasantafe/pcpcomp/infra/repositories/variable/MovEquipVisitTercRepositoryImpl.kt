package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToMovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.modelRoomToMovEquipVisitTerc

class MovEquipVisitTercRepositoryImpl(
    private val movEquipVisitTercRoomDatasource: MovEquipVisitTercRoomDatasource,
): MovEquipVisitTercRepository {

    override suspend fun listOpen(): Result<List<MovEquipVisitTerc>> {
        try {
            val resultListOpen = movEquipVisitTercRoomDatasource.listOpen()
            if(resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val result = resultListOpen.getOrNull()!!.map {
                it.modelRoomToMovEquipVisitTerc()
            }
            return Result.success(
                result
            )
        } catch (e: Exception) {
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.listOpen",
                    cause = e
                )
            )
        }
    }

    override suspend fun setClose(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean> {
        try {
            val movEquipVisitTercRoomModel = movEquipVisitTerc.entityToMovEquipVisitTercRoomModel(
                matricVigia = movEquipVisitTerc.nroMatricVigiaMovEquipVisitTerc!!,
                idLocal = movEquipVisitTerc.idLocalMovEquipVisitTerc!!
            )
            val resultSetClose = movEquipVisitTercRoomDatasource.setClose(movEquipVisitTercRoomModel)
            if(resultSetClose.isFailure)
                return Result.failure(resultSetClose.exceptionOrNull()!!)
            return Result.success(resultSetClose.getOrNull()!!)
        } catch (e: Exception){
            return Result.failure(
                RepositoryException(
                    function = "MovEquipVisitTercRepositoryImpl.setClose",
                    cause = e
                )
            )
        }
    }

}