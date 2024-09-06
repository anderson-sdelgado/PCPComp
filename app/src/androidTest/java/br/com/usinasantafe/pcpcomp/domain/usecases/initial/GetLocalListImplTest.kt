package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Local
import br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.SaveAllLocal
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class GetLocalListImplTest: KoinTest {

    private val usecase: GetLocalList by inject()
    private val saveAllLocal: SaveAllLocal by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success() = runTest {
        val locals = listOf(
            Local(
                idLocal = 1,
                descrLocal = "USINA"
            ),
        )
        saveAllLocal(locals)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, locals)
    }

    @Test
    fun check_return_error() = runTest {
        val exception = try {
            usecase()
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

}