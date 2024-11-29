package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.MovEquipProprioApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipProprioRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IMovEquipProprioRetrofitDatasourceTest {

    @Test
    fun `Check return correct`() = runTest {

        val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = 1,
            dthrMovEquipProprio = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            nroAparelhoMovEquipProprio = 16997417840L,
            movEquipProprioEquipSegList = emptyList(),
            movEquipProprioPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""[{"idMovEquipProprio":1}]"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipProprioApi = retrofit.create(MovEquipProprioApi::class.java)
        val datasource = IMovEquipProprioRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                movEquipProprioRetrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = 1,
            dthrMovEquipProprio = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            nroAparelhoMovEquipProprio = 16997417840L,
            movEquipProprioEquipSegList = emptyList(),
            movEquipProprioPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipProprioApi = retrofit.create(MovEquipProprioApi::class.java)
        val datasource = IMovEquipProprioRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                movEquipProprioRetrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            NullPointerException().toString()
        )
    }

    @Test
    fun `Check return failure if data sent incorrect`() = runTest {
        val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = 1,
            dthrMovEquipProprio = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            nroAparelhoMovEquipProprio = 16997417840L,
            movEquipProprioEquipSegList = emptyList(),
            movEquipProprioPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("Failure Connection BD"))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipProprioApi = retrofit.create(MovEquipProprioApi::class.java)
        val datasource = IMovEquipProprioRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                movEquipProprioRetrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
        )
    }

    @Test
    fun `Check return failure if have data incorrect`() = runTest {
        val movEquipProprioRetrofitModelOutput = MovEquipProprioRetrofitModelOutput(
            idMovEquipProprio = 1,
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = 1,
            dthrMovEquipProprio = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR")).format(
                Date(1723213270250)
            ),
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            nroAparelhoMovEquipProprio = 16997417840L,
            movEquipProprioEquipSegList = emptyList(),
            movEquipProprioPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""[{"idMovEquipProprio":1a}]"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipProprioApi = retrofit.create(MovEquipProprioApi::class.java)
        val datasource = IMovEquipProprioRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                movEquipProprioRetrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 23 path \$[0].idMovEquipProprio"
        )
    }
}