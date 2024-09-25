package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipVisitTercRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipVisitTercRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToMovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.modelRoomToMovEquipVisitTerc
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc

class MovEquipVisitTercRepositoryImpl(
    private val movEquipVisitTercSharedPreferencesDatasource: MovEquipVisitTercSharedPreferencesDatasource,
    private val movEquipVisitTercRoomDatasource: MovEquipVisitTercRoomDatasource,
): MovEquipVisitTercRepository {

    override suspend fun get(id: Int): Result<MovEquipVisitTerc> {
        TODO("Not yet implemented")
    }

    override suspend fun getDestino(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getIdVisitTerc(id: Int): Result<Int> {
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

    override suspend fun getTypeVisitTerc(
        flowApp: FlowApp,
        id: Int
    ): Result<TypeVisitTerc> {
        when(flowApp){
            FlowApp.ADD -> {
                val resultGet = movEquipVisitTercSharedPreferencesDatasource.get()
                if(resultGet.isFailure)
                    return Result.failure(resultGet.exceptionOrNull()!!)
                return Result.success(resultGet.getOrNull()!!.tipoVisitTercMovEquipVisitTerc!!)
            }
            FlowApp.CHANGE -> {
                val resultGet = movEquipVisitTercRoomDatasource.get(id)
                if(resultGet.isFailure)
                    return Result.failure(resultGet.exceptionOrNull()!!)
                return Result.success(resultGet.getOrNull()!!.tipoVisitTercMovEquipVisitTerc)
            }
        }
    }

    override suspend fun getVeiculo(id: Int): Result<String> {
        TODO("Not yet implemented")
    }

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

    override suspend fun listOpenInput(): Result<List<MovEquipVisitTerc>> {
        TODO("Not yet implemented")
    }

    override suspend fun save(matricVigia: Int, idLocal: Int): Result<Int> {
        TODO("Not yet implemented")
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

    override suspend fun setDestino(
        destino: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setIdVisitTerc(
        idVisitTerc: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setObservVisitTerc(
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

    override suspend fun setTipoVisitTerc(typeVisitTerc: TypeVisitTerc): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setVeiculo(
        veiculo: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun start(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun start(movEquipVisitTerc: MovEquipVisitTerc): Result<Boolean> {
        TODO("Not yet implemented")
    }


}