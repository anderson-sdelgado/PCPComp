package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CheckSendMovProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SendMovProprioList
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetStatusSentMovProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.CheckSendMovResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SendMovResidenciaList
import br.com.usinasantafe.pcpcomp.domain.usecases.residencia.SetStatusSentMovResidencia
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.CheckSendMovVisitTerc
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SendMovVisitTercList
import br.com.usinasantafe.pcpcomp.domain.usecases.visitterc.SetStatusSentMovVisitTerc
import br.com.usinasantafe.pcpcomp.utils.StatusSend

class ProcessWorkManager(
    context: Context,
    workerParameters: WorkerParameters,
    private val checkSendMovProprio: CheckSendMovProprio,
    private val sendMovProprioList: SendMovProprioList,
    private val setStatusSentMovProprio: SetStatusSentMovProprio,
    private val checkSendMovVisitTerc: CheckSendMovVisitTerc,
    private val sendMovVisitTercList: SendMovVisitTercList,
    private val setStatusSentMovVisitTerc: SetStatusSentMovVisitTerc,
    private val checkSendMovResidencia: CheckSendMovResidencia,
    private val sendMovResidenciaList: SendMovResidenciaList,
    private val setStatusSentResidencia: SetStatusSentMovResidencia,
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val resultCheckProprio = checkSendMovProprio()
        if(resultCheckProprio.isFailure)
            return Result.retry()
        val checkSendProprio = resultCheckProprio.getOrNull()!!
        if(checkSendProprio){
            val resultSendProprioList = sendMovProprioList()
            if(resultSendProprioList.isFailure)
                return Result.retry()
            val resultSet = setStatusSentMovProprio(resultSendProprioList.getOrNull()!!)
            if(resultSet.isFailure)
                return Result.retry()
        }
        val resultCheckVisitTerc = checkSendMovVisitTerc()
        if(resultCheckVisitTerc.isFailure)
            return Result.retry()
        val checkSendVisitTerc = resultCheckVisitTerc.getOrNull()!!
        if(checkSendVisitTerc){
            val resultSendVisitTerc = sendMovVisitTercList()
            if(resultSendVisitTerc.isFailure)
                return Result.retry()
            val resultSetVisitTerc = setStatusSentMovVisitTerc(resultSendVisitTerc.getOrNull()!!)
            if(resultSetVisitTerc.isFailure)
                return Result.retry()
        }
        val resultCheckResidencia = checkSendMovResidencia()
        if(resultCheckResidencia.isFailure)
            return Result.retry()
        val checkSendResidencia = resultCheckResidencia.getOrNull()!!
        if(checkSendResidencia){
            val resultSendResidencia = sendMovResidenciaList()
            if(resultSendResidencia.isFailure)
                return Result.retry()
            val resultSetResidencia = setStatusSentResidencia(resultSendResidencia.getOrNull()!!)
            if(resultSetResidencia.isFailure)
                return Result.retry()
        }
        return Result.success()
    }
}