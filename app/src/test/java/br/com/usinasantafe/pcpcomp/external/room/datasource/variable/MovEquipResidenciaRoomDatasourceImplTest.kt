package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipResidenciaDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipResidenciaRoomModel
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
class MovEquipResidenciaRoomDatasourceImplTest {

    private lateinit var movEquipResidenciaDao: MovEquipResidenciaDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipResidenciaDao = db.movEquipResidenciaDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check exception if not have mov`() = runTest {
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        val exception = try {
            datasource.listOpen()
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun `Check return true if have mov open`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        val resultSave = datasource.save(movEquipResidenciaRoomModel)
        val id = resultSave.getOrNull()!!
        assertEquals(id.toInt(), 1)
        val resultList = datasource.listOpen()
        assertEquals(resultList.isSuccess, true)
        val model = resultList.getOrNull()!![0]
        assertEquals(model.dthrMovEquipResidencia, movEquipResidenciaRoomModel.dthrMovEquipResidencia)
        assertEquals(model.placaMovEquipResidencia, movEquipResidenciaRoomModel.placaMovEquipResidencia)
    }

    @Test
    fun `Check return failure in get if not have mov with id researched`() = runTest {
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.get(1)
        assertTrue(result.isSuccess)
        val roomModel = result.getOrNull()!!
        assertEquals(roomModel.observMovEquipResidencia, "OBSERV TESTE")
    }

    @Test
    fun `Check return true if setMotorista execute correctly`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        movEquipResidenciaDao.insert(
            movEquipResidenciaRoomModel
        )
        val roomModelBefore = movEquipResidenciaDao.get(1)
        assertEquals(roomModelBefore.statusMovEquipForeigResidencia, StatusForeigner.INSIDE)
        val result = datasource.setOutside(roomModelBefore)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.statusMovEquipForeigResidencia, StatusForeigner.OUTSIDE)
    }

    @Test
    fun `Check return true if setPlaca execute correctly`() = runTest {
        val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
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
            nroMatricVigiaMovEquipResidencia = 19759,
            idLocalMovEquipResidencia = 1,
            tipoMovEquipResidencia = TypeMov.INPUT,
            dthrMovEquipResidencia = 1723213270250,
            motoristaMovEquipResidencia = "MOTORISTA TESTE",
            veiculoMovEquipResidencia = "VEICULO TESTE",
            placaMovEquipResidencia = "PLACA TESTE",
            observMovEquipResidencia = "OBSERV TESTE",
            statusMovEquipResidencia = StatusData.OPEN,
            statusSendMovEquipResidencia = StatusSend.SEND,
            statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
        )
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        datasource.save(movEquipResidenciaRoomModel)
        val result = datasource.setVeiculo("VEICULO TESTE ALTERAR", 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val roomModelAfter = movEquipResidenciaDao.get(1)
        assertEquals(roomModelAfter.veiculoMovEquipResidencia, "VEICULO TESTE ALTERAR")
        assertEquals(roomModelAfter.statusSendMovEquipResidencia, StatusSend.SEND)
    }

}