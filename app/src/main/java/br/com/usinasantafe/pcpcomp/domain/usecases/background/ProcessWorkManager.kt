package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ProcessWorkManager(
    context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
//        Thread.sleep(1000)
        return Result.success()
    }
}