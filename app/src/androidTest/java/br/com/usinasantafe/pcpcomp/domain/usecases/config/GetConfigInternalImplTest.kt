package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class GetConfigInternalImplTest: KoinTest {

    private val usecase: GetConfigInternal by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_return_null_if_dont_data_config() = runTest {
        val result = usecase()
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun verify_return_config_if_have_data_config() = runTest {
        val config = Config(
            number = 16997417840,
            password = "12345"
        )
        configSharedPreferences.save(config)
        val result = usecase()
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.number, "16997417840")
        assertEquals(result.getOrNull()!!.password, "12345")
    }

}