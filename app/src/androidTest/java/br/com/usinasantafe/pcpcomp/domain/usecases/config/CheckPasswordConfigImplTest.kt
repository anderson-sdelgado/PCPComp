package br.com.usinasantafe.pcpcomp.domain.usecases.config

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CheckPasswordConfigImplTest: KoinTest {

    private val usecase: CheckPasswordConfig by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun verify_return_true_if_dont_data_config() = runTest {
        val result = usecase("12345")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }

    @Test
    fun verify_return_false_if_password_typed_incorrect() = runTest {
        configSharedPreferences.save(Config(password = "12345"))
        val result = usecase("123456")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, false)
    }

    @Test
    fun verify_return_true_if_password_typed_correct() = runTest {
        configSharedPreferences.save(Config(password = "12345"))
        val result = usecase("12345")
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, true)
    }
}