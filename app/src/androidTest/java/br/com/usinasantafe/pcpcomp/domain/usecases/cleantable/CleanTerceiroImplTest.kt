package br.com.usinasantafe.pcpcomp.domain.usecases.cleantable

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CleanTerceiroImplTest: KoinTest {

    private val usecase: CleanColab by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_clean_terceiro_correct() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }

}