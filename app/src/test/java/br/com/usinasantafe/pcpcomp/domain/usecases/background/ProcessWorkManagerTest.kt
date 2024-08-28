package br.com.usinasantafe.pcpcomp.domain.usecases.background

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.TestWorkerBuilder
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(RobolectricTestRunner::class)
class ProcessWorkManagerTest {

    private lateinit var context: Context

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testSleepWorker() = runTest {
        val worker = TestListenableWorkerBuilder<ProcessWorkManager>(
            context = context,
        ).build()
        val result = worker.doWork()
        assertEquals(result, ListenableWorker.Result.retry())
    }
}