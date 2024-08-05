package br.com.usinasantafe.pcpcomp.external.webservices.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Terceiro
import br.com.usinasantafe.pcpcomp.external.webservices.api.stable.TerceiroApi
import br.com.usinasantafe.pcpcomp.external.webservices.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test

class TerceiroRetrofitDatasourceImplTest {

    @Test
    fun `Check return failure if token is invalid`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("{ error : Authorization header is missing }"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: TerceiroApi = retrofit.create(TerceiroApi::class.java)
        val datasource = TerceiroRetrofitDatasourceImpl(service)
        val result = datasource.recoverAll("12345")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: TerceiroApi = retrofit.create(TerceiroApi::class.java)
        val datasource = TerceiroRetrofitDatasourceImpl(service)
        val result = datasource.recoverAll("12345")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun `Check return correct`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultTerceiroRetrofit))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: TerceiroApi = retrofit.create(TerceiroApi::class.java)
        val datasource = TerceiroRetrofitDatasourceImpl(service)
        val result = datasource.recoverAll("12345")
        assertTrue(result.isSuccess)
        assertEquals(
            result,
            Result.success(
                listOf(
                    Terceiro(
                        idTerceiro = 1,
                        idBDTerceiro = 1,
                        cpfTerceiro = "123.456.789-00",
                        nomeTerceiro = "Terceiro",
                        empresaTerceiro = "Empresa Terceiro"
                    )
                )
            )
        )
    }

}

val resultTerceiroRetrofit = """
    [{"idTerceiro":1,"idBDTerceiro":1,"cpfTerceiro":"123.456.789-00","nomeTerceiro":"Terceiro","empresaTerceiro":"Empresa Terceiro"}]
""".trimIndent()