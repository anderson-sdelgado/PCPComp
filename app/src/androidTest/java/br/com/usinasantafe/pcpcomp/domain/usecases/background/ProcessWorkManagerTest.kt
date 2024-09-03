package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import androidx.work.workDataOf
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class ProcessWorkManagerTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = getApplicationContext()

        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun doWork() {

        // Create request
        val request = OneTimeWorkRequestBuilder<ProcessWorkManager>()
            .build()

        val workManager = WorkManager.getInstance(context)
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(request).result.get()
        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertEquals(workInfo.state, WorkInfo.State.ENQUEUED)

    }

    @Test
    fun doWork2() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Create request
        val request = OneTimeWorkRequestBuilder<ProcessWorkManager>()
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor.
        workManager.enqueue(request).result.get()
        // Get WorkInfo and outputData
        testDriver?.setAllConstraintsMet(request.id)
        val workInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertEquals(workInfo.state, WorkInfo.State.SUCCEEDED)

    }
}