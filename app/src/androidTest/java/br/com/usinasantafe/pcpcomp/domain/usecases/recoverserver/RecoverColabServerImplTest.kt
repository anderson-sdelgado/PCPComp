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

class RecoverColabServerImplTest : KoinTest {

    private val usecase: RecoverColabServer by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_failure_usecase_if_not_have_data_config_internal() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultColab))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_if_access_invalid_page() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.saveConfig(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_if_token_invalid() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(tokenInvalidColab))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.saveConfig(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")
    }


    @Test
    fun verify_return_failure_repository_if_data_incorrect() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultColabIncorrect))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.saveConfig(
            Config(
                number = 16997417840,
                password = "12345",
                version = "6.00",
                idBD = 1
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.NullPointerException: Parameter specified as non-null is null: method br.com.usinasantafe.pcpcomp.domain.entities.stable.Colab.<init>, parameter nomeColab")
    }

    @Test
    fun verify_return_data_if_success_usecase() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultColab))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        configSharedPreferences.saveConfig(
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
        val resultColab = resultList[0]
        assertEquals(resultColab.matricColab, 19759)
        assertEquals(resultColab.nomeColab, "ANDERSON DA SILVA DELGADO")
    }

}

val resultColab = """
    [{"matricColab":19759,"nomeColab":"ANDERSON DA SILVA DELGADO"}]
""".trimIndent()

val resultColabIncorrect = """
    [{"matricColab":19759}]
""".trimIndent()

val tokenInvalidColab = """
    {
        "error": "Invalid token"
    }
""".trimIndent()