package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipVisitTercDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeVisitTerc
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipVisitTercRoomDatasourceImplTest {

    private lateinit var movEquipVisitTercDao: MovEquipVisitTercDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        movEquipVisitTercDao = db.movEquipVisitTercDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check exception if not have mov`() = runTest {
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        val exception = try {
            datasource.listOpen()
            null
        } catch (exception: Exception) {
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun `Check return true if have mov open`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        movEquipVisitTercDao.insert(
            movEquipVisitTercRoomModel
        )
        val result = datasource.listOpen()
        assertEquals(result.isSuccess, true)
        val model = result.getOrNull()!![0]
        assertEquals(model.veiculoMovEquipVisitTerc, "VEICULO TESTE")
    }

    @Test
    fun `Check alter status in mov open set`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        movEquipVisitTercDao.insert(
            movEquipVisitTercRoomModel
        )
        val roomModelBefore = movEquipVisitTercDao.get(1)
        assertEquals(roomModelBefore.statusMovEquipVisitTerc, StatusData.OPEN)
        val result = datasource.setClose(roomModelBefore)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.statusMovEquipVisitTerc, StatusData.CLOSE)
    }

    @Test
    fun `Check add movEquipVisitTerc`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        val resultSave = datasource.save(movEquipVisitTercRoomModel)
        val id = resultSave.getOrNull()!!
        assertEquals(id.toInt(), 1)
        val resultList = datasource.listOpen()
        assertEquals(resultList.isSuccess, true)
        val model = resultList.getOrNull()!![0]
        assertEquals(model.dthrMovEquipVisitTerc, movEquipVisitTercRoomModel.dthrMovEquipVisitTerc)
        assertEquals(
            model.placaMovEquipVisitTerc,
            movEquipVisitTercRoomModel.placaMovEquipVisitTerc
        )
    }

    @Test
    fun `Check return failure in get if not have mov with id researched`() = runTest {
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        val exception = try {
            datasource.get(1)
            null
        } catch (exception: Exception) {
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun `Check return roomModel if get execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.get(1)
        assertTrue(result.isSuccess)
        val roomModel = result.getOrNull()!!
        assertEquals(roomModel.observMovEquipVisitTerc, "OBSERV TESTE")
    }

    @Test
    fun `Check return true if setDestino execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.setDestino("TESTE ALTERAR DESTINO", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.destinoMovEquipVisitTerc, "TESTE ALTERAR DESTINO")
        assertEquals(roomModelAfter.statusSendMovEquipVisitTerc, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setIdVisitTerc execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.setIdVisitTerc(10, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.idVisitTercMovEquipVisitTerc, 10)
        assertEquals(roomModelAfter.statusSendMovEquipVisitTerc, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setObserv execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.setObserv("OBSERV TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.observMovEquipVisitTerc, "OBSERV TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipVisitTerc, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setObserv execute correctly is null`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.setObserv(null, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.observMovEquipVisitTerc, null)
        assertEquals(roomModelAfter.statusSendMovEquipVisitTerc, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setPlaca execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.setPlaca("PLACA TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.placaMovEquipVisitTerc, "PLACA TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipVisitTerc, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setVeiculo execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val result = datasource.setVeiculo("VEICULO TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.veiculoMovEquipVisitTerc, "VEICULO TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipVisitTerc, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setOutside execute correctly`() = runTest {
        val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(movEquipVisitTercRoomModel)
        val roomModelBefore = movEquipVisitTercDao.get(1)
        assertEquals(roomModelBefore.statusMovEquipForeigVisitTerc, StatusForeigner.INSIDE)
        val result = datasource.setOutside(roomModelBefore)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(roomModelAfter.statusMovEquipForeigVisitTerc, StatusForeigner.OUTSIDE)
    }

    @Test
    fun `CheckSend - Check return true if have mov send`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
            datasource.save(movEquipVisitTercRoomModel)
            val result = datasource.checkSend()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `CheckSend - Check return false if not have mov send`() =
        runTest {
            val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
            val result = datasource.checkSend()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `Check return list if have mov send`() = runTest {
        val roomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(roomModel)
        val result = datasource.listSend()
        assertEquals(result.isSuccess, true)
        val list = result.getOrNull()!!
        assertEquals(list.size, 1)
        val entity = list[0]
        assertEquals(entity.dthrMovEquipVisitTerc, roomModel.dthrMovEquipVisitTerc)
        assertEquals(entity.destinoMovEquipVisitTerc, "DESTINO TESTE")
    }

    @Test
    fun `Check return list empty if not have mov send`() = runTest {
        val roomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SENT,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(roomModel)
        val result = datasource.listSend()
        assertEquals(result.isSuccess, true)
        val list = result.getOrNull()!!
        assertEquals(list.size, 0)
    }

    @Test
    fun `setSent - Check return failure in setSent if not have mov`() = runTest {
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        val result = datasource.setSent(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRoomDatasourceImpl.setSent"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel.setStatusSendMovEquipVisitTerc(br.com.usinasantafe.pcpcomp.utils.StatusSend)\" because \"roomModel\" is null"
        )
    }

    @Test
    fun `setSent - Check return true if setSent execute correctly`() = runTest {
        val roomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SEND,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(roomModel)
        val result = datasource.setSent(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(
            roomModelAfter.observMovEquipVisitTerc,
            "OBSERV TESTE"
        )
        assertEquals(
            roomModelAfter.statusSendMovEquipVisitTerc,
            StatusSend.SENT
        )
    }

    @Test
    fun `setSend - Check return failure in setSend if not have mov`() = runTest {
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        val result = datasource.setSend(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipVisitTercRoomDatasourceImpl.setSend"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipVisitTercRoomModel.setStatusSendMovEquipVisitTerc(br.com.usinasantafe.pcpcomp.utils.StatusSend)\" because \"roomModel\" is null"
        )
    }

    @Test
    fun `setSend - Check return true if setSend execute correctly`() = runTest {
        val roomModel = MovEquipVisitTercRoomModel(
            nroMatricVigiaMovEquipVisitTerc = 19759,
            idLocalMovEquipVisitTerc = 1,
            tipoMovEquipVisitTerc = TypeMov.INPUT,
            idVisitTercMovEquipVisitTerc = 1000,
            tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
            dthrMovEquipVisitTerc = 1723213270250,
            veiculoMovEquipVisitTerc = "VEICULO TESTE",
            placaMovEquipVisitTerc = "PLACA TESTE",
            destinoMovEquipVisitTerc = "DESTINO TESTE",
            observMovEquipVisitTerc = "OBSERV TESTE",
            statusMovEquipVisitTerc = StatusData.OPEN,
            statusSendMovEquipVisitTerc = StatusSend.SENT,
            statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        datasource.save(roomModel)
        val result = datasource.setSend(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val movEquipProprioRoomModelAfter = movEquipVisitTercDao.get(1)
        assertEquals(
            movEquipProprioRoomModelAfter.observMovEquipVisitTerc,
            "OBSERV TESTE"
        )
        assertEquals(
            movEquipProprioRoomModelAfter.statusSendMovEquipVisitTerc,
            StatusSend.SEND
        )
    }

    @Test
    fun `Check data returned in listSent`() =
        runTest {
            val roomModel1 = MovEquipVisitTercRoomModel(
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SENT,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            val roomModel2 = MovEquipVisitTercRoomModel(
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SENT,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            val roomModel3 = MovEquipVisitTercRoomModel(
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            movEquipVisitTercDao.insert(roomModel1)
            movEquipVisitTercDao.insert(roomModel2)
            movEquipVisitTercDao.insert(roomModel3)
            val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
            val result = datasource.listSent()
            assertEquals(result.isSuccess, true)
            val list = result.getOrNull()!!
            assertEquals(list.size, 2)
            assertEquals(list[0].idMovEquipVisitTerc, 1)
            assertEquals(list[1].idMovEquipVisitTerc, 2)
        }

    @Test
    fun `CheckOpen - Check return true if have mov open`() =
        runTest {
            val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
                nroMatricVigiaMovEquipVisitTerc = 19759,
                idLocalMovEquipVisitTerc = 1,
                tipoMovEquipVisitTerc = TypeMov.INPUT,
                idVisitTercMovEquipVisitTerc = 1000,
                tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
                dthrMovEquipVisitTerc = 1723213270250,
                veiculoMovEquipVisitTerc = "VEICULO TESTE",
                placaMovEquipVisitTerc = "PLACA TESTE",
                destinoMovEquipVisitTerc = "DESTINO TESTE",
                observMovEquipVisitTerc = "OBSERV TESTE",
                statusMovEquipVisitTerc = StatusData.OPEN,
                statusSendMovEquipVisitTerc = StatusSend.SEND,
                statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
            )
            val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
            datasource.save(movEquipVisitTercRoomModel)
            val result = datasource.checkOpen()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `CheckOpen - Check return false if not have mov open`() =
        runTest {
            val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
            val result = datasource.checkOpen()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

}