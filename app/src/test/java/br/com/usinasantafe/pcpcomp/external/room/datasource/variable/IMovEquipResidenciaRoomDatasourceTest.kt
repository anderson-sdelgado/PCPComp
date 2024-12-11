package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipResidenciaRoomDatasourceTest {

    private lateinit var movEquipResidenciaDao: MovEquipResidenciaDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        movEquipResidenciaDao = db.movEquipResidenciaDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check exception if not have mov`() = runTest {
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
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
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        movEquipResidenciaDao.insert(
            movEquipResidenciaRoomModel
        )
        val result = datasource.listOpen()
        assertEquals(result.isSuccess, true)
        val model = result.getOrNull()!![0]
        assertEquals(model.motoristaMovEquipResidencia, "MOTORISTA TESTE")
        assertEquals(model.placaMovEquipResidencia, "PLACA TESTE")
    }

    @Test
    fun `Check alter status in mov open set`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        movEquipResidenciaDao.insert(
            movEquipResidenciaRoomModel
        )
        val roomModelBefore = movEquipResidenciaDao.get(1)
        assertEquals(roomModelBefore.statusMovEquipResidencia, StatusData.OPEN)
        val result = datasource.setClose(roomModelBefore)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.statusMovEquipResidencia, StatusData.CLOSE)
    }

    @Test
    fun `Check add movEquipResidencia`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        val resultSave = datasource.save(movEquipResidenciaRoomModel)
        val id = resultSave.getOrNull()!!
        assertEquals(id.toInt(), 1)
        val resultList = datasource.listOpen()
        assertEquals(resultList.isSuccess, true)
        val model = resultList.getOrNull()!![0]
        assertEquals(
            model.dthrMovEquipResidencia,
            movEquipResidenciaRoomModel.dthrMovEquipResidencia
        )
        assertEquals(
            model.placaMovEquipResidencia,
            movEquipResidenciaRoomModel.placaMovEquipResidencia
        )
    }

    @Test
    fun `Check return failure in get if not have mov with id researched`() = runTest {
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
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
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.get(1)
        assertTrue(result.isSuccess)
        val roomModel = result.getOrNull()!!
        assertEquals(roomModel.observMovEquipResidencia, "OBSERV TESTE")
    }

    @Test
    fun `Check return true if setMotorista execute correctly`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.setMotorista("MOTORISTA TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.motoristaMovEquipResidencia, "MOTORISTA TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipResidencia, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setObserv execute correctly`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.setObserv("OBSERV TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.observMovEquipResidencia, "OBSERV TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipResidencia, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setObserv execute correctly null`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.setObserv(null, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.observMovEquipResidencia, null)
        assertEquals(roomModelAfter.statusSendMovEquipResidencia, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setOutside execute correctly null`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        movEquipResidenciaDao.insert(
            movEquipResidenciaRoomModel
        )
        val roomModelBefore = movEquipResidenciaDao.get(1)
        assertEquals(roomModelBefore.statusMovEquipForeignerResidencia, StatusForeigner.INSIDE)
        val result = datasource.setOutside(roomModelBefore)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.statusMovEquipForeignerResidencia, StatusForeigner.OUTSIDE)
    }

    @Test
    fun `Check return true if setPlaca execute correctly`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.setPlaca("PLACA TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.placaMovEquipResidencia, "PLACA TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipResidencia, StatusSend.SEND)
    }

    @Test
    fun `Check return true if setVeiculo execute correctly`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.setVeiculo("VEICULO TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.veiculoMovEquipResidencia, "VEICULO TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipResidencia, StatusSend.SEND)
    }

    @Test
    fun `CheckSend - Check return true if have mov send`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
            datasource.save(movEquipResidenciaRoomModel)
            val result = datasource.checkSend()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `CheckSend - Check return false if not have mov send`() =
        runTest {
            val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
            val result = datasource.checkSend()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

    @Test
    fun `Check return list if have mov send`() = runTest {
        val roomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(roomModel)
        val result = datasource.listSend()
        assertEquals(result.isSuccess, true)
        val list = result.getOrNull()!!
        assertEquals(list.size, 1)
        val entity = list[0]
        assertEquals(entity.dthrMovEquipResidencia, roomModel.dthrMovEquipResidencia)
        assertEquals(entity.motoristaMovEquipResidencia, "MOTORISTA TESTE")
    }

    @Test
    fun `Check return list empty if not have mov send`() = runTest {
        val roomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SENT,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(roomModel)
        val result = datasource.listSend()
        assertEquals(result.isSuccess, true)
        val list = result.getOrNull()!!
        assertEquals(list.size, 0)
    }

    @Test
    fun `setSent - Check return failure in setSent if not have mov`() = runTest {
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        val result = datasource.setSent(1)
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> MovEquipResidenciaRoomDatasourceImpl.setSent"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "java.lang.NullPointerException: Cannot invoke \"br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel.setStatusSendMovEquipResidencia(br.com.usinasantafe.pcpcomp.utils.StatusSend)\" because \"roomModel\" is null"
        )
    }

    @Test
    fun `setSent - Check return true if setSent execute correctly`() = runTest {
        val roomModel = MovEquipResidenciaRoomModel(
            matricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMovEquip.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
        )
        val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
        datasource.save(roomModel)
        val result = datasource.setSent(1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(
            roomModelAfter.statusSendMovEquipResidencia,
            StatusSend.SENT
        )
        assertEquals(
            roomModelAfter.observMovEquipResidencia,
            "OBSERV TESTE"
        )
    }

    @Test
    fun `Check data returned in listSent`() =
        runTest {
            val roomModel1 = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SENT,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val roomModel2 = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 2,
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SENT,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val roomModel3 = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 3,
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            movEquipResidenciaDao.insert(roomModel1)
            movEquipResidenciaDao.insert(roomModel2)
            movEquipResidenciaDao.insert(roomModel3)
            val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
            val result = datasource.listSent()
            assertEquals(result.isSuccess, true)
            val list = result.getOrNull()!!
            assertEquals(list.size, 2)
            assertEquals(list[0].idMovEquipResidencia, 1)
            assertEquals(list[1].idMovEquipResidencia, 2)
        }

    @Test
    fun `Check data returned in delete`() =
        runTest {
            val roomModel1 = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 1,
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SENT,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val roomModel2 = MovEquipResidenciaRoomModel(
                idMovEquipResidencia = 2,
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SENT,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            movEquipResidenciaDao.insert(roomModel1)
            movEquipResidenciaDao.insert(roomModel2)
            val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
            val result = datasource.delete(roomModel1)
            assertEquals(result.isSuccess, true)
            val resultList = datasource.listSent()
            assertEquals(resultList.isSuccess, true)
            val list = resultList.getOrNull()!!
            assertEquals(list.size, 1)
            assertEquals(list[0].idMovEquipResidencia, 2)
        }

    @Test
    fun `CheckOpen - Check return true if have mov open`() =
        runTest {
            val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
                matricVigiaMovEquipResidencia = 19759,
                idLocalMovEquipResidencia = 1,
                tipoMovEquipResidencia = TypeMovEquip.INPUT,
                dthrMovEquipResidencia = 1723213270250,
                motoristaMovEquipResidencia = "MOTORISTA TESTE",
                veiculoMovEquipResidencia = "VEICULO TESTE",
                placaMovEquipResidencia = "PLACA TESTE",
                observMovEquipResidencia = "OBSERV TESTE",
                statusMovEquipResidencia = StatusData.OPEN,
                statusSendMovEquipResidencia = StatusSend.SEND,
                statusMovEquipForeignerResidencia = StatusForeigner.INSIDE,
            )
            val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
            datasource.save(movEquipResidenciaRoomModel)
            val result = datasource.checkOpen()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
        }

    @Test
    fun `CheckOpen - Check return false if not have mov open`() =
        runTest {
            val datasource = IMovEquipResidenciaRoomDatasource(movEquipResidenciaDao)
            val result = datasource.checkOpen()
            assertTrue(result.isSuccess)
            assertFalse(result.getOrNull()!!)
        }

}