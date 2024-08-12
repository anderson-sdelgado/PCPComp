package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
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

    private val movEquipProprioRoomModel = MovEquipProprioRoomModel(
        idMovEquipProprio = 1,
        nroMatricVigiaMovEquipProprio = 19759,
        idLocalMovEquipProprio = 1,
        tipoMovEquipProprio = TypeMov.INPUT,
        dthrMovEquipProprio = 1723213270250,
        idEquipMovEquipProprio = 1,
        nroMatricColabMovEquipProprio = 19759,
        destinoMovEquipProprio = "TESTE DESTINO",
        nroNotaFiscalMovEquipProprio = 123456789,
        observMovEquipProprio = "TESTE OBSERV",
        statusMovEquipProprio = StatusData.OPEN,
        statusSendMovEquipProprio = StatusSend.SEND
    )

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
    fun `Check return true if have mov open`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        movEquipProprioDao.insert(
            movEquipProprioRoomModel
        )
        val result = datasource.listOpen()
        assertEquals(result.isSuccess, true)
        val resultList = result.getOrNull()!!
        assertEquals(resultList[0], movEquipProprioRoomModel)
    }

    @Test
    fun `Check alter status in mov open set`() = runTest {
        val datasource = MovEquipProprioRoomDatasourceImpl(movEquipProprioDao)
        movEquipProprioDao.insert(
            movEquipProprioRoomModel
        )
        val movEquipProprioRoomModelBefore = movEquipProprioDao.getId(movEquipProprioRoomModel.idMovEquipProprio!!)
        assertEquals(movEquipProprioRoomModelBefore.statusMovEquipProprio, StatusData.OPEN)
        val result = datasource.setClose(movEquipProprioRoomModel)
        val movEquipProprioRoomModelAfter = movEquipProprioDao.getId(movEquipProprioRoomModel.idMovEquipProprio!!)
        assertEquals(result.isSuccess, true)
        assertEquals(result.getOrNull()!!, true);
        assertEquals(movEquipProprioRoomModelAfter.statusMovEquipProprio, StatusData.CLOSE)
    }

}