package br.com.usinasantafe.pcpcomp.domain.usecases.initial

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class GetNomeVigiaImplTest: KoinTest {

    private val usecase: GetNomeVigia by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()
    private val colabRoomDatasource: ColabRoomDatasource by inject()

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
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverNomeVigia")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException")
    }

    @Test
    fun check_return_success() = runTest {
        configSharedPreferences.save(
            Config(matricVigia = 19759)
        )
        colabRoomDatasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "ANDERSON DA SILVA DELGADO")
    }
}