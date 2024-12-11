package br.com.usinasantafe.pcpcomp.external.retrofit.datasource.variable

import br.com.usinasantafe.pcpcomp.external.retrofit.api.variable.MovEquipResidenciaApi
import br.com.usinasantafe.pcpcomp.external.retrofit.provideRetrofitTest
import br.com.usinasantafe.pcpcomp.infra.models.retrofit.variable.MovEquipResidenciaRetrofitModelOutput
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class IMovEquipResidenciaRetrofitDatasourceTest {

    @Test
    fun `Check return correct`() = runTest {
        val retrofitModelOutput = MovEquipResidenciaRetrofitModelOutput(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT.ordinal + 1,
            dthrMovEquipResidencia = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            nroAparelhoMovEquipResidencia = 16997417840L
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("""[{"idMovEquipResidencia":1}]""")
        )
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipResidenciaApi = retrofit.create(MovEquipResidenciaApi::class.java)
        val datasource = IMovEquipResidenciaRetrofitDatasource(service)
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
        val retrofitModelOutput = MovEquipResidenciaRetrofitModelOutput(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT.ordinal + 1,
            dthrMovEquipResidencia = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            nroAparelhoMovEquipResidencia = 16997417840L
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipResidenciaApi = retrofit.create(MovEquipResidenciaApi::class.java)
        val datasource = IMovEquipResidenciaRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            NullPointerException().toString()
        )
    }

    @Test
    fun `Check return failure if data sent incorrect`() = runTest {
        val retrofitModelOutput = MovEquipResidenciaRetrofitModelOutput(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT.ordinal + 1,
            dthrMovEquipResidencia = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            nroAparelhoMovEquipResidencia = 16997417840L
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("Failure Connection BD")
        )
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipResidenciaApi = retrofit.create(MovEquipResidenciaApi::class.java)
        val datasource = IMovEquipResidenciaRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
        )
    }

    @Test
    fun `Check return failure if have data incorrect`() = runTest {
        val retrofitModelOutput = MovEquipResidenciaRetrofitModelOutput(
            idMovEquipResidencia = 1,
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT.ordinal + 1,
            dthrMovEquipResidencia = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale("pt", "BR")
            ).format(
                Date(1723213270250)
            ),
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            nroAparelhoMovEquipResidencia = 16997417840L
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(
            MockResponse().setBody("""[{"idMovEquipResidencia":1a}]""")
        )
        val retrofit = provideRetrofitTest(server.url("").toString())
        val service: MovEquipResidenciaApi = retrofit.create(MovEquipResidenciaApi::class.java)
        val datasource = IMovEquipResidenciaRetrofitDatasource(service)
        val result = datasource.send(
            listOf(
                retrofitModelOutput
            )
            , "123456"
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRetrofitDatasourceImpl.send"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 26 path \$[0].idMovEquipResidencia"
        )
    }
}