package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.getserver

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IGetServerChaveTest : KoinTest {

    private val usecase: GetServerChave by inject()
    private val configSharedPreferences: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_failure_usecase_if_not_have_data_config_internal() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultChave)
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("/").toString()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Usecase -> GetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                NullPointerException().toString()
            )
        }

    @Test
    fun verify_return_failure_datasource_if_access_invalid_page() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("/").toString()
                )
            )
            configSharedPreferences.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "6.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IChaveRetrofitDatasource.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                NullPointerException().toString()
            )
        }

    @Test
    fun verify_return_failure_datasource_if_token_invalid() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(tokenInvalidChave)
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("/").toString()
                )
            )
            configSharedPreferences.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "6.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IChaveRetrofitDatasource.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
        }

    @Test
    fun verify_return_failure_repository_if_data_incorrect() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultChaveIncorrect)
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("/").toString()
                )
            )
            configSharedPreferences.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "6.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IChaveRetrofitDatasource.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Expected value at line 1 column 52 path \$[0].idLocalTrab"
            )
        }

    @Test
    fun verify_return_data_if_success_usecase() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultChave)
            )
            loadKoinModules(
                generateTestAppComponent(
                    server.url("/").toString()
                )
            )
            configSharedPreferences.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "6.00",
                    idBD = 1
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val resultList = result.getOrNull()!!
            val resultChave = resultList[0]
            assertEquals(
                resultChave.idChave,
                1
            )
            assertEquals(
                resultChave.descrChave,
                "USINA"
            )
            assertEquals(
                resultChave.idLocalTrab,
                1
            )
        }
}

val resultChave = """
    [{"idChave":1,"descrChave":"01 - TI","idLocalTrab":1}]
""".trimIndent()

val resultChaveIncorrect = """
    [{"idChave":1,"descrChave":"01 - TI","idLocalTrab":}]
""".trimIndent()

val tokenInvalidChave = """
    {
        "error": "Invalid token"
    }
""".trimIndent()