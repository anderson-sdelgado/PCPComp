package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetStatusSendConfigImplTest: KoinTest {

    private val usecase: SetStatusSendConfig by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1,
        )
        configSharedPreferences.save(config)
        val result = usecase(StatusSend.SENDING)
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }

    @Test
    fun check_status_send_in_data_Config() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345",
            version = "6.00",
            idBD = 1,
        )
        configSharedPreferences.save(config)
        usecase(StatusSend.SENDING)
        val result = configSharedPreferences.get()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.statusSend, StatusSend.SENDING)
    }
}