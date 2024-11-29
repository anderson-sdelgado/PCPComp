package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.MovEquipVisitTercApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipVisitTercRetrofitModelOutput
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IMovEquipVisitTercRetrofitDatasourceTest {

    @Test
    fun `Check return correct`() = runTest {
        val retrofitModelOutput = MovEquipVisitTercRetrofitModelOutput(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT.ordinal + 1,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
            dthrMovEquipVisitTerc = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            nroAparelhoMovEquipVisitTerc = 16997417840L,
            movEquipVisitTercPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("""[{"idMovEquipVisitTerc":1}]""")
        )
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipVisitTercApi = retrofit.create(MovEquipVisitTercApi::class.java)
        val datasource = IMovEquipVisitTercRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Check return failure if have Error 404`() = runTest {
        val retrofitModelOutput = MovEquipVisitTercRetrofitModelOutput(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT.ordinal + 1,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
            dthrMovEquipVisitTerc = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            nroAparelhoMovEquipVisitTerc = 16997417840L,
            movEquipVisitTercPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipVisitTercApi = retrofit.create(MovEquipVisitTercApi::class.java)
        val datasource = IMovEquipVisitTercRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            NullPointerException().toString()
        )
    }

    @Test
    fun `Check return failure if data sent incorrect`() = runTest {
        val retrofitModelOutput = MovEquipVisitTercRetrofitModelOutput(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT.ordinal + 1,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
            dthrMovEquipVisitTerc = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            nroAparelhoMovEquipVisitTerc = 16997417840L,
            movEquipVisitTercPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("Failure Connection BD")
        )
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipVisitTercApi = retrofit.create(MovEquipVisitTercApi::class.java)
        val datasource = IMovEquipVisitTercRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
        )
    }

    @Test
    fun `Check return failure if have data incorrect`() = runTest {
        val retrofitModelOutput = MovEquipVisitTercRetrofitModelOutput(
            idMovEquipVisitTerc = 1,
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT.ordinal + 1,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO.ordinal + 1,
            dthrMovEquipVisitTerc = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            nroAparelhoMovEquipVisitTerc = 16997417840L,
            movEquipVisitTercPassagList = emptyList(),
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""[{"idMovEquipVisitTerc":1a}]"""))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipVisitTercApi = retrofit.create(MovEquipVisitTercApi::class.java)
        val datasource = IMovEquipVisitTercRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 25 path \$[0].idMovEquipVisitTerc"
        )
    }
}