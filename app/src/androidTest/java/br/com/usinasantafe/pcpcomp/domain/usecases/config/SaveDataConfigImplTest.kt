package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllColab
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveDataConfigImplTest: KoinTest {

    private val usecase: SaveDataConfig by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val result = usecase(
            idBD = 1,
            number = "16997417840",
            version = "6.00",
            password = "12345"
        )
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }

    @Test
    fun check_return_failure_if_number_is_incorrect() = runTest {
        val result = usecase(
            idBD = 1,
            number = "16997417840A",
            version = "6.00",
            password = "12345"
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SaveDataConfig")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NumberFormatException: For input string: \"16997417840A\"")
    }

}