package br.com.usinasantafe.pcpcomp.external.webservices.datasource.variable

import br.com.usinasantafe.pcpcomp.external.webservices.api.variable.ConfigApi
import br.com.usinasantafe.pcpcomp.external.webservices.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelInput
import br.com.usinasantafe.pcpcomp.infra.models.webservice.ConfigWebServiceModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test


class ConfigRetrofitDatasourceImplTest {

    @Test
    fun `Check return correct`() = runTest {
        val configWebServiceModelOutput = ConfigWebServiceModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""{"idBD":1}"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = ConfigRetrofitDatasourceImpl(service)
        val result = datasource.recoverToken(configWebServiceModelOutput)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), ConfigWebServiceModelInput(idBD = 1))
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val configWebServiceModelOutput = ConfigWebServiceModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = ConfigRetrofitDatasourceImpl(service)
        val result = datasource.recoverToken(configWebServiceModelOutput)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun `Check return failure if data sent incorrect`() = runTest {
        val configWebServiceModelOutput = ConfigWebServiceModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("Failure Connection BD"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = ConfigRetrofitDatasourceImpl(service)
        val result = datasource.recoverToken(configWebServiceModelOutput)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$")
    }

    @Test
    fun `Check return failure if have data incorrect`() = runTest {
        val configWebServiceModelOutput = ConfigWebServiceModelOutput(
            version = "6.00",
            number = 16997417840,
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""{"idBD":1a}"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: ConfigApi = retrofit.create(ConfigApi::class.java)
        val datasource = ConfigRetrofitDatasourceImpl(service)
        val result = datasource.recoverToken(configWebServiceModelOutput)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> ConfigRetrofitDatasourceImpl.recoverToken")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 9 path \$.idBD")
    }
}
