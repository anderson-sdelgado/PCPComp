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
import br.com.usinasantafe.pcpcomp.utils.TypeMov
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

    private val movEquipResidenciaRoomModel = MovEquipResidenciaRoomModel(
        idMovEquipResidencia = 1,
        nroMatricVigiaMovEquipResidencia = 1000,
        idLocalMovEquipResidencia = 1000,
        tipoMovEquipResidencia = TypeMov.INPUT,
        dthrMovEquipResidencia = 1723213270250,
        motoristaMovEquipResidencia = "TESTE",
        veiculoMovEquipResidencia = "TESTE",
        placaMovEquipResidencia = "TESTE",
        observMovEquipResidencia = "TESTE",
        statusMovEquipResidencia = StatusData.OPEN,
        statusSendMovEquipResidencia = StatusSend.SEND,
        statusMovEquipForeigResidencia = StatusForeigner.INSIDE,
    )

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
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        movEquipResidenciaDao.insert(
            movEquipResidenciaRoomModel
        )
        val result = datasource.listOpen()
        assertEquals(result.isSuccess, true)
        val resultList = result.getOrNull()!!
        assertEquals(resultList[0], movEquipResidenciaRoomModel)
    }

    @Test
    fun `Check alter status in mov open set`() = runTest {
        val datasource = MovEquipResidenciaRoomDatasourceImpl(movEquipResidenciaDao)
        movEquipResidenciaDao.insert(
            movEquipResidenciaRoomModel
        )
        val movEquipResidenciaRoomModelBefore = movEquipResidenciaDao.getId(movEquipResidenciaRoomModel.idMovEquipResidencia!!)
        assertEquals(movEquipResidenciaRoomModelBefore.statusMovEquipResidencia, StatusData.OPEN)
        val result = datasource.setClose(movEquipResidenciaRoomModel)
        val movEquipResidenciaRoomModelAfter = movEquipResidenciaDao.getId(movEquipResidenciaRoomModel.idMovEquipResidencia!!)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true);
        assertEquals(movEquipResidenciaRoomModelAfter.statusMovEquipResidencia, StatusData.CLOSE)
    }

}