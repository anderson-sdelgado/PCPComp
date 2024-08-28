package br.com.usinasantafe.pcpcomp.infra.repositories.variable

import br.com.usinasantafe.pcpcomp.domain.entities.variable.MovEquipProprio
import br.com.usinasantafe.pcpcomp.domain.errors.RepositoryException
import br.com.usinasantafe.pcpcomp.domain.repositories.variable.MovEquipProprioRepository
import br.com.usinasantafe.pcpcomp.infra.datasource.room.variable.MovEquipProprioRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.entityToRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.pcpcomp.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov

class MovEquipProprioRepositoryImpl(
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource,
    private val movEquipProprioRoomDatasource: MovEquipProprioRoomDatasource,
) : MovEquipProprioRepository {

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

    override suspend fun save(
        matricVigia: Int,
        idLocal: Int
    ): Result<Int> {
        try {
            val resultGetMov = movEquipProprioSharedPreferencesDatasource.get()
            if (resultGetMov.isFailure)
                return Result.failure(resultGetMov.exceptionOrNull()!!)
            val movEquipProprioRoomModel =
                resultGetMov.getOrNull()!!.sharedPreferencesModelToEntity().entityToRoomModel(matricVigia, idLocal)
            val resultSave = movEquipProprioRoomDatasource.save(movEquipProprioRoomModel)
            if (resultSave.isFailure)
                return Result.failure(resultSave.exceptionOrNull()!!)
            val resultClear = movEquipProprioSharedPreferencesDatasource.clear()
            if (resultClear.isFailure)
                return Result.failure(resultClear.exceptionOrNull()!!)
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

    override suspend fun setClose(movEquipProprio: MovEquipProprio): Result<Boolean> {
        try {
            val movEquipProprioRoomModel = movEquipProprio.entityToRoomModel(
                matricVigia = movEquipProprio.nroMatricVigiaMovEquipProprio!!,
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
                FlowApp.CHANGE -> TODO()
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
                FlowApp.CHANGE -> TODO()
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

    override suspend fun setNotaFiscal(
        notaFiscal: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setNotaFiscal(notaFiscal)
                FlowApp.CHANGE -> TODO()
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

    override suspend fun setMatricColab(
        matricColab: Int,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when (flowApp) {
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setMatricColab(matricColab)
                FlowApp.CHANGE -> TODO()
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

    override suspend fun setObserv(
        observ: String,
        flowApp: FlowApp,
        id: Int
    ): Result<Boolean> {
        return try {
            when(flowApp){
                FlowApp.ADD -> movEquipProprioSharedPreferencesDatasource.setObserv(observ)
                FlowApp.CHANGE -> TODO()
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

    override suspend fun start(typeMov: TypeMov): Result<Boolean> {
        return movEquipProprioSharedPreferencesDatasource.start(typeMov)
    }

}