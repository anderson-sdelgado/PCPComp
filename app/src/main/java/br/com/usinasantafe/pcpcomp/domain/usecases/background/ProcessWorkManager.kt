package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetStatusSendConfig
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.CheckSendMovProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SendMovProprio
import br.com.usinasantafe.pcpcomp.domain.usecases.proprio.SetStatusSentMovProprio
import br.com.usinasantafe.pcpcomp.utils.StatusSend

class ProcessWorkManager(
    context: Context,
    workerParameters: WorkerParameters,
    private val setStatusSendConfig: SetStatusSendConfig,
    private val checkSendMovProprio: CheckSendMovProprio,
    private val sendMovProprio: SendMovProprio,
    private val setStatusSentMovProprio: SetStatusSentMovProprio
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        setStatusSendConfig(StatusSend.SENDING)
        val resultCheckSend = checkSendMovProprio()
        val checkSendMovProprio = resultCheckSend.getOrNull()!!
        if(checkSendMovProprio){
            val resultSend = sendMovProprio()
            if(resultSend.isFailure)
                return Result.retry()
            setStatusSentMovProprio(resultSend.getOrNull()!!)
        }
        return Result.success()
    }
}