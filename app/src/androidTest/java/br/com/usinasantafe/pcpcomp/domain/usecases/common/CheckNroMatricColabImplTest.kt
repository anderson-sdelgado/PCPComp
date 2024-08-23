package br.com.usinasantafe.pcpcomp.domain.usecases.common

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CheckNroMatricColabImplTest: KoinTest {

    private val usecase: CheckMatricColab by inject()
    private val colabDao: ColabDao by inject()

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
            ColabRoomModel(
                matricColab = 19759,
                nomeColab = "ANDERSON DA SILVA DELGADO"
            )
        )
        colabDao.insertAll(colabList)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }
}