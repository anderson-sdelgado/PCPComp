package br.com.usinasantafe.pcpcomp.domain.usecases.recoverserver

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class RecoverLocalServerImplTest : KoinTest {

    private val usecase: RecoverLocalServer by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_failure_usecase_if_not_have_data_config_internal() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultLocal))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> RecoverLocalServer")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_if_access_invalid_page() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_if_token_invalid() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(tokenInvalidLocal))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> LocalRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")
    }

    @Test
    fun verify_return_failure_repository_if_data_incorrect() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultLocalIncorrect))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> LocalRepositoryImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException: Parameter specified as non-null is null: method br.com.usinasantafe.pcpcomp.domain.entities.stable.Local.<init>, parameter descrLocal")
    }

    @Test
    fun verify_return_data_if_success_usecase() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultLocal))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.save(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        val resultList = result.getOrNull()!!
        val resultLocal = resultList[0]
        assertEquals(resultLocal.idLocal, 1)
        assertEquals(resultLocal.descrLocal, "USINA")
    }

}

val resultLocal = """
    [{"idLocal":1,"descrLocal":"USINA"}]
""".trimIndent()

val resultLocalIncorrect = """
    [{"idLocal":1}]
""".trimIndent()

val tokenInvalidLocal = """
    {
        "error": "Invalid token"
    }
""".trimIndent()