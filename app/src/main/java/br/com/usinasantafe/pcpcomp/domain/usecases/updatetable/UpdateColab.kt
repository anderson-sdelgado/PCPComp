package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable

import br.com.usinasantafe.pcpcomp.domain.entities.ResultUpdate
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.cleantable.CleanColab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver.GetAllColabServer
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savealltable.SaveAllColab
import br.com.usinasantafe.pcpcomp.utils.Errors
import br.com.usinasantafe.pcpcomp.utils.TB_COLAB
import br.com.usinasantafe.pcpcomp.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UpdateColab {
    suspend operator fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate>
}

class UpdateColabImpl(
    private val cleanColab: CleanColab,
    private val getAllColabServer: GetAllColabServer,
    private val saveAllColab: SaveAllColab,
): UpdateColab  {

    override suspend fun invoke(sizeAll: Float, count: Float): Flow<ResultUpdate> = flow {
        var pos = 0f
        emit(
            ResultUpdate(
                flagProgress = true,
                msgProgress = "Recuperando dados da tabela $TB_COLAB do Web Service",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultRecover = getAllColabServer()
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
                msgProgress = "Limpando a tabela $TB_COLAB",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val resultClean = cleanColab()
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
                msgProgress = "Salvando dados na tabela $TB_COLAB",
                currentProgress = updatePercentage(++pos, count, sizeAll)
            )
        )
        val list = resultRecover.getOrNull()!!
        val resultSave = saveAllColab(list)
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