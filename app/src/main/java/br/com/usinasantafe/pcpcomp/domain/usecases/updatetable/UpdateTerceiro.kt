package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanTerceiro
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllTerceiroServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllTerceiro
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TB_TERCEIRO
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UpdateTerceiro {
    suspend operator fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate>
}

class UpdateTerceiroImpl(
    private val cleanTerceiro: CleanTerceiro,
    private val getAllTerceiroServer: GetAllTerceiroServer,
    private val saveAllTerceiro: SaveAllTerceiro,
): UpdateTerceiro  {

    override suspend fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate> = flow {
        var pos = 0f
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_TERCEIRO do Web Service",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultRecover = getAllTerceiroServer()
        if (resultRecover.isFailure) {
            val error = resultRecover.exceptionOrNull()!!
            val failure =
                "${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Limpando a tabela $TB_TERCEIRO",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultClean = cleanTerceiro()
        if (resultClean.isFailure) {
            val error = resultClean.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Salvando dados na tabela $TB_TERCEIRO",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllTerceiro(list)
        if (resultSave.isFailure) {
            val error = resultSave.exceptionOrNull()!!
            val failure = "${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdate(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    msgProgress = failure,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

}