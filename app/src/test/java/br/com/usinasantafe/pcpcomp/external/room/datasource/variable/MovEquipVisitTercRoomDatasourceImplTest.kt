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
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipVisitTercDao = db.movEquipVisitTercDao()
    }

    @After
    fun after() {
        db.close()
    }

    private val movEquipVisitTercRoomModel = MovEquipVisitTercRoomModel(
        idMovEquipVisitTerc = 1,
        nroMatricVigiaMovEquipVisitTerc = 1000,
        idLocalMovEquipVisitTerc = 1000,
        tipoMovEquipVisitTerc = TypeMov.INPUT,
        idVisitTercMovEquipVisitTerc = 1000,
        tipoVisitTercMovEquipVisitTerc = TypeVisitTerc.TERCEIRO,
        dthrMovEquipVisitTerc = 1723213270250,
        veiculoMovEquipVisitTerc = "TESTE",
        placaMovEquipVisitTerc = "TESTE",
        destinoMovEquipVisitTerc = "TESTE",
        observMovEquipVisitTerc = "TESTE",
        statusMovEquipVisitTerc = StatusData.OPEN,
        statusSendMovEquipVisitTerc = StatusSend.SEND,
        statusMovEquipForeigVisitTerc = StatusForeigner.INSIDE,
    )

    @Test
    fun `Check exception if not have mov`() = runTest {
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
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
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        movEquipVisitTercDao.insert(
            movEquipVisitTercRoomModel
        )
        val result = datasource.listOpen()
        assertEquals(result.isSuccess, true)
        val resultList = result.getOrNull()!!
        assertEquals(resultList[0], movEquipVisitTercRoomModel)
    }

    @Test
    fun `Check alter status in mov open set`() = runTest {
        val datasource = MovEquipVisitTercRoomDatasourceImpl(movEquipVisitTercDao)
        movEquipVisitTercDao.insert(
            movEquipVisitTercRoomModel
        )
        val movEquipVisitTercRoomModelBefore = movEquipVisitTercDao.getId(movEquipVisitTercRoomModel.idMovEquipVisitTerc!!)
        assertEquals(movEquipVisitTercRoomModelBefore.statusMovEquipVisitTerc, StatusData.OPEN)
        val result = datasource.setClose(movEquipVisitTercRoomModel)
        val movEquipVisitTercRoomModelAfter = movEquipVisitTercDao.getId(movEquipVisitTercRoomModel.idMovEquipVisitTerc!!)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true);
        assertEquals(movEquipVisitTercRoomModelAfter.statusMovEquipVisitTerc, StatusData.CLOSE)
    }

}