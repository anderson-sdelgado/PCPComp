package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.RLocalFluxoApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.RLocalFluxoRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class RLocalFluxoRetrofitDatasourceImplTest {

    @Test
    fun `Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    "{ error : Authorization header is missing }"
                )
            )
            val retrofit = provideRetrofitTest(server.url("").toString())
            val service = retrofit.create(RLocalFluxoApi::class.java)
            val datasource = RLocalFluxoRetrofitDatasourceImpl(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> RLocalFluxoRetrofitDatasourceImpl.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
        }

    @Test
    fun `Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(server.url("").toString())
            val service = retrofit.create(RLocalFluxoApi::class.java)
            val datasource = RLocalFluxoRetrofitDatasourceImpl(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> RLocalFluxoRetrofitDatasourceImpl.recoverAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(
                    resultRLocalFluxoRetrofit
                )
            )
            val retrofit = provideRetrofitTest(server.url("").toString())
            val service = retrofit.create(RLocalFluxoApi::class.java)
            val datasource = RLocalFluxoRetrofitDatasourceImpl(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    listOf(
                        RLocalFluxoRetrofitModel(
                            idRLocalFluxo = 1,
                            idFluxo = 1,
                            idLocal = 1
                        )
                    )
                )
            )
        }
}

val resultRLocalFluxoRetrofit = """
    [{"idRLocalFluxo":1,"idFluxo":1,"idLocal":1}]
""".trimIndent()