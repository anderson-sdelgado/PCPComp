package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipProprioRoomDatasourceImplTest {

    private lateinit var movEquipProprioDao: MovEquipProprioDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipProprioDao = db.movEquipProprioDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check exception if not have mov`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val exception = try {
            datasource.listOpen()
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun `Check return list if have mov open`() = runTest {
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        movEquipProprioDao.insert(
            movEquipProprioRoomModel
        )
        val result = datasource.listOpen()
        assertEquals(result.isSuccess, true)
        val resultList = result.getOrNull()!!
        assertEquals(resultList[0].dthrMovEquipProprio, movEquipProprioRoomModel.dthrMovEquipProprio)
    }

    @Test
    fun `Check alter status in mov open set`() = runTest {
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val id = movEquipProprioDao.insert(
            movEquipProprioRoomModel
        )
        val movEquipProprioRoomModelBefore = movEquipProprioDao.get(id.toInt())
        assertEquals(movEquipProprioRoomModelBefore.statusMovEquipProprio, StatusData.OPEN)
        val result = datasource.setClose(movEquipProprioRoomModelBefore)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(id.toInt())
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
        assertEquals(movEquipProprioRoomModelAfter.statusMovEquipProprio, StatusData.CLOSE)
    }

    @Test
    fun `Check add movEquipProprio`() = runTest {
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val resultSave = datasource.save(movEquipProprioRoomModel)
        val id = resultSave.getOrNull()!!
        assertEquals(id.toInt(), 1)
        val resultList = datasource.listOpen()
        assertEquals(resultList.isSuccess, true)
        val model = resultList.getOrNull()!![0]
        assertEquals(model.dthrMovEquipProprio, movEquipProprioRoomModel.dthrMovEquipProprio)
        assertEquals(model.destinoMovEquipProprio, movEquipProprioRoomModel.destinoMovEquipProprio)
    }

    @Test
    fun `Check return true if have mov send`() = runTest {
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val resultCheckSend = datasource.checkSend()
        assertTrue(resultCheckSend.isSuccess)
        assertTrue(resultCheckSend.getOrNull()!!)
    }

    @Test
    fun `Check return false if not have mov send`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val result = datasource.checkSend()
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check return list if have mov send`() = runTest {
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.listSend()
        assertEquals(result.isSuccess, true)
        val resultList = result.getOrNull()!!
        assertEquals(resultList[0].dthrMovEquipProprio, movEquipProprioRoomModel.dthrMovEquipProprio)
        assertEquals(resultList[0].idMovEquipProprio, 1)
    }

    @Test
    fun `Sent - Check return failure if not have mov`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val result = datasource.setSent(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRoomDatasourceImpl.setSent"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel.setStatusSendMovEquipProprio(br.com.usinasantafe.pcpcomp.utils.StatusSend)\" because \"movEquipProprioRoomModel\" is null"
        )
    }

    @Test
    fun `Sent - Check return true if setSent execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setSent(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(
            movEquipProprioRoomModelAfter.observMovEquipProprio,
            "TESTE OBSERV"
        )
        assertEquals(
            movEquipProprioRoomModelAfter.statusSendMovEquipProprio,
            StatusSend.SENT
        )
    }

    @Test
    fun `Check return failure in get if not have mov with id researched`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val exception = try {
            datasource.get(1)
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun `Check return roomModel if get execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.get(1)
        assertTrue(result.isSuccess)
        val roomModel = result.getOrNull()!!
        assertEquals(roomModel.observMovEquipProprio, "TESTE OBSERV")
    }

    @Test
    fun `Check return true if setIdEquip execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setIdEquip(10, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.idEquipMovEquipProprio, 10)
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setMatricColab execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setMatricColab(18017, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.matricColabMovEquipProprio, 18017)
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setDestino execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setDestino("TESTE ALTERAR DESTINO", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.destinoMovEquipProprio, "TESTE ALTERAR DESTINO")
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setNotaFiscal execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setNotaFiscal(123456, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.notaFiscalMovEquipProprio, 123456)
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setNotaFiscal execute correctly and value is null`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setNotaFiscal(null, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.notaFiscalMovEquipProprio, null)
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setObserv execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setObserv("TESTE ALTERAR OBSERV", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.observMovEquipProprio, "TESTE ALTERAR OBSERV")
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setObserv execute correctly and value is null`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
            statusSendMovEquipProprio = StatusSend.SENDING
        )
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setObserv(null, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(movEquipProprioRoomModelAfter.observMovEquipProprio, null)
        assertEquals(movEquipProprioRoomModelAfter.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun `Send - Check return failure if not have mov`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        val result = datasource.setSend(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipProprioRoomDatasourceImpl.setSend"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel.setStatusSendMovEquipProprio(br.com.usinasantafe.pcpcomp.utils.StatusSend)\" because \"movEquipProprioRoomModel\" is null"
        )
    }

    @Test
    fun `Send - Check return true if setSend execute correctly`() = runTest {
        val movEquipProprioRoomModel = MovEquipProprioRoomModel(
            idMovEquipProprio = 1,
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
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        datasource.save(movEquipProprioRoomModel)
        val result = datasource.setSend(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.get(1)
        assertEquals(
            movEquipProprioRoomModelAfter.observMovEquipProprio,
            "TESTE OBSERV"
        )
        assertEquals(
            movEquipProprioRoomModelAfter.statusSendMovEquipProprio,
            StatusSend.SEND
        )
    }

}