package br.com.usinasantafe.pcpcomp.domain.usecases.initial

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

class AdjustConfigImplTest: KoinTest {

    private val usecase: AdjustConfig by inject()
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_not_have_data() =
        runTest {
            val result = usecase("1.00")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun check_return_true_and_data_returned_if_version_is_different() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    version = "5.30"
                )
            )
            val result = usecase("6.00")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultHas = configSharedPreferencesDatasource.has()
            assertTrue(resultHas.isSuccess)
            assertFalse(resultHas.getOrNull()!!)
        }

    @Test
    fun check_return_true_and_data_returned_if_version_is_equal() =
        runTest {
            configSharedPreferencesDatasource.save(
                Config(
                    version = "6.00"
                )
            )
            val result = usecase("6.00")
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultHas = configSharedPreferencesDatasource.has()
            assertTrue(resultHas.isSuccess)
            assertTrue(resultHas.getOrNull()!!)
        }
}