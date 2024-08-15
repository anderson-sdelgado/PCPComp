package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.initial.RecoverNomeVigia
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class RecoverNomeColabImplTest: KoinTest {

    private val usecase: RecoverNomeColab by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() = runTest {
        val result = usecase("19759")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverNomeColab")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun check_return_success() = runTest {
        val colabDao: ColabDao by inject()
        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }
}