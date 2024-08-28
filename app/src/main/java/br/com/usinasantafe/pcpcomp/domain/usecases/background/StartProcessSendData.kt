package br.com.usinasantafe.pcpcomp.domain.usecases.background

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

interface StartProcessSendData {
    suspend operator fun invoke()
}

class StartProcessSendDataImpl(
    private val workManager: WorkManager,
) : StartProcessSendData {

    override suspend fun invoke() {
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(ProcessWorkManager::class.java)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                2, TimeUnit.MINUTES
            )
            .build()
        workManager.enqueueUniqueWork("WORKMANAGER-PCP", ExistingWorkPolicy.REPLACE, workRequest)
    }

}