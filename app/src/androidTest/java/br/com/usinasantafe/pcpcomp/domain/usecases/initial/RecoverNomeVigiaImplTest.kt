package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class RecoverNomeVigiaImplTest: KoinTest {

    private val usecase: RecoverNomeVigia by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() = runTest {
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }
}