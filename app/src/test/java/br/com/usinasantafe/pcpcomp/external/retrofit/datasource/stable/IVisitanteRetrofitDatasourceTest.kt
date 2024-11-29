package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.stable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Visitante
import br.com.usinasantafe.pcpcomp.external.retrofit.api.stable.VisitanteApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test

class IVisitanteRetrofitDatasourceTest {

    @Test
    fun `Check return failure if token is invalid`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("{ error : Authorization header is missing }"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: VisitanteApi = retrofit.create(VisitanteApi::class.java)
        val datasource = IVisitanteRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> VisitanteRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: VisitanteApi = retrofit.create(VisitanteApi::class.java)
        val datasource = IVisitanteRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> VisitanteRetrofitDatasourceImpl.recoverAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun `Check return correct`() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(resultVisitanteRetrofit))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: VisitanteApi = retrofit.create(VisitanteApi::class.java)
        val datasource = IVisitanteRetrofitDatasource(service)
        val result = datasource.recoverAll("12345")
        assertTrue(result.isSuccess)
        assertEquals(
            result,
            Result.success(
                listOf(
                    Visitante(
                        idVisitante = 1,
                        cpfVisitante = "123.456.789-00",
                        nomeVisitante = "Visitante",
                        empresaVisitante = "Empresa Visitante"
                    )
                )
            )
        )
    }

}

val resultVisitanteRetrofit = """
    [{"idVisitante":1,"cpfVisitante":"123.456.789-00","nomeVisitante":"Visitante","empresaVisitante":"Empresa Visitante"}]
""".trimIndent()