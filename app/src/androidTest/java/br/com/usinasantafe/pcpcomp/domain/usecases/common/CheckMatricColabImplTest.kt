package br.com.usinasantafe.pcpcomp.domain.usecases.common

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

class CheckMatricColabImplTest: KoinTest {

    private val usecase: CheckMatricColab by inject()
    private val save: SaveAllColab by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_false_if_matric_is_invalid() = runTest {
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(false))
    }

    @Test
    fun check_return_true_if_matric_is_valid() = runTest {
        val colabList = listOf(
            Colab(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        save(colabList)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }
}