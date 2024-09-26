package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipResidencia
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipResidenciaRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipResidenciaRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToMovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.modelRoomToMovEquipResidencia
import br.com.usinasantafe.pcpcomp.utils.FlowApp

class MovEquipResidenciaRepositoryImpl(
    private val movEquipResidenciaRoomDatasource: MovEquipResidenciaRoomDatasource,
): MovEquipResidenciaRepository {

    override suspend fun get(id: Int): Result<MovEquipResidencia> {
        TODO("Not yet implemented")
    }

    override suspend fun getMotorista(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getObserv(id: Int): Result<String?> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlaca(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getVeiculo(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun listOpen(): Result<List<MovEquipResidencia>> {
        try {
            val resultListOpen = movEquipResidenciaRoomDatasource.listOpen()
            if(resultListOpen.isFailure)
                return Result.failure(resultListOpen.exceptionOrNull()!!)
            val result = resultListOpen.getOrNull()!!.map {
                it.modelRoomToMovEquipResidencia()
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

    override suspend fun listOpenInput(): Result<List<MovEquipResidencia>> {
        TODO("Not yet implemented")
    }

    override suspend fun setClose(movEquipResidencia: MovEquipResidencia): Result<Boolean> {
        try {
            val movEquipResidenciaRoomModel = movEquipResidencia.entityToMovEquipResidenciaRoomModel(
                matricVigia = movEquipResidencia.nroMatricVigiaMovEquipResidencia!!,
                idLocal = movEquipResidencia.idLocalMovEquipResidencia!!
            )
            val resultSetClose = movEquipResidenciaRoomDatasource.setClose(movEquipResidenciaRoomModel)
            if(resultSetClose.isFailure)
                return Result.failure(resultSetClose.exceptionOrNull()!!)
            return Result.success(resultSetClose.getOrNull()!!)
        } catch (e: Exception){
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
        TODO("Not yet implemented")
    }

    override suspend fun setObserv(
        observ: String?,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setPlaca(
        placa: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}