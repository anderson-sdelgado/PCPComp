package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.ChaveApi
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ChaveRetrofitModel
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.stable.ColabRetrofitModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test

class IChaveRetrofitDatasourceTest {

    @Test
    fun `Check return failure if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ChaveApi::class.java)
            val datasource = IChaveRetrofitDatasource(service)
            val result = datasource.recoverAll("12345")
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
    fun `Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ChaveApi::class.java)
            val datasource = IChaveRetrofitDatasource(service)
            val result = datasource.recoverAll("12345")
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
    fun `Check return correct`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultChaveRetrofit)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(ChaveApi::class.java)
            val datasource = IChaveRetrofitDatasource(service)
            val result = datasource.recoverAll("12345")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(
                    listOf(
                        ChaveRetrofitModel(
                            idChave = 1,
                            descrChave = "01 - TI",
                            idLocalTrab = 1
                        )
                    )
                )
            )
        }
}

val resultChaveRetrofit = """
    [{"idChave":1,"descrChave":"01 - TI","idLocalTrab":1}]
""".trimIndent()
