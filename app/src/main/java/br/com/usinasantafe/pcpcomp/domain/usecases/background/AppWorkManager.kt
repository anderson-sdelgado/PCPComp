package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import androidx.work.WorkManager

fun providerWorkManager(appContext: Context): WorkManager {
    return WorkManager.getInstance(appContext)
}