package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.FluxoApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.FluxoRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IFluxoRetrofitDatasourceTest {

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
            val service = retrofit.create(FluxoApi::class.java)
            val datasource = IFluxoRetrofitDatasource(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> FluxoRetrofitDatasourceImpl.recoverAll"
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
            val service = retrofit.create(FluxoApi::class.java)
            val datasource = IFluxoRetrofitDatasource(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> FluxoRetrofitDatasourceImpl.recoverAll"
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
                    resultFluxoRetrofit
                )
            )
            val retrofit = provideRetrofitTest(server.url("").toString())
            val service = retrofit.create(FluxoApi::class.java)
            val datasource = IFluxoRetrofitDatasource(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    listOf(
                        FluxoRetrofitModel(
                            idFluxo= 1,
                            descrFluxo = "MOV. EQUIP. PRÓPRIO"
                        )
                    )
                )
            )
        }
}

val resultFluxoRetrofit = """
    [{"idFluxo":1,"descrFluxo":"MOV. EQUIP. PRÓPRIO"}]
""".trimIndent()