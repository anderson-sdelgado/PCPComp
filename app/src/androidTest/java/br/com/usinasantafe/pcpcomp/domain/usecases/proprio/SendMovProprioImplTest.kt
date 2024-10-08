package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SendMovProprioImplTest : KoinTest {

    private val usecase: SendMovProprioList by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource by inject()

    @Test
    fun verify_return_true() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = 1723213270250,
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""[{"idMovEquipProprio":1}]"""))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        movEquipProprioDao.insert(movEquipProprioRoomModel)
        configSharedPreferencesDatasource.save(
            Config(
                idBD = 1,
                number = 16997417840,
                version = "6.00",
                password = "12345"
            )
        )
        val result = usecase()
        assertTrue(result.isSuccess)
        val listInput = result.getOrNull()!!
        assertEquals(listInput[0].idMovEquipProprio, 1)
    }

    @Test
    fun verify_return_failure_usecase_if_not_have_mov_send() = runTest {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""[{"idMovEquipProprio":1}]"""))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SendMovProprioImpl")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_usecase_if_not_have_config() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = 1723213270250,
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody("""[{"idMovEquipProprio":1}]"""))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        movEquipProprioDao.insert(movEquipProprioRoomModel)
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Usecase -> SendMovProprioImpl")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = 1723213270250,
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(404))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        movEquipProprioDao.insert(movEquipProprioRoomModel)
        configSharedPreferencesDatasource.save(
            Config(
                idBD = 1,
                number = 16997417840,
                version = "6.00",
                password = "12345"
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioRetrofitDatasourceImpl.send")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), NullPointerException().toString())
    }

    @Test
    fun verify_return_failure_datasource_return_empty() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            matricVigiaMovEquipProprio = 19759,
            idLocalMovEquipProprio = 1,
            tipoMovEquipProprio = TypeMov.INPUT,
            dthrMovEquipProprio = 1723213270250,
            idEquipMovEquipProprio = 1,
            matricColabMovEquipProprio = 19759,
            destinoMovEquipProprio = "TESTE DESTINO",
            notaFiscalMovEquipProprio = 123456789,
            observMovEquipProprio = "TESTE OBSERV",
            statusMovEquipProprio = StatusData.OPEN,
            statusSendMovEquipProprio = StatusSend.SEND
        )
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(""))
        loadKoinModules(generateTestAppComponent(server.url("/").toString()))
        movEquipProprioDao.insert(movEquipProprioRoomModel)
        configSharedPreferencesDatasource.save(
            Config(
                idBD = 1,
                number = 16997417840,
                version = "6.00",
                password = "12345"
            )
        )
        val result = usecase()
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> MovEquipProprioRetrofitDatasourceImpl.send")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "java.io.EOFException: End of input at line 1 column 1 path \$")
    }
}