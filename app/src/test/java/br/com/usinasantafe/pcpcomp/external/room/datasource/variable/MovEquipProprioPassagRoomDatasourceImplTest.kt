package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioPassagRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MovEquipProprioPassagRoomDatasourceImplTest {

    private lateinit var movEquipProprioPassagDao: MovEquipProprioPassagDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        movEquipProprioPassagDao = db.movEquipProprioPassagDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = MovEquipProprioPassagRoomDatasourceImpl(movEquipProprioPassagDao)
        val result = datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
                )
            )
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check success list if have MovEquipProprioPassagRoomModel list`() = runTest {
        val datasource = MovEquipProprioPassagRoomDatasourceImpl(movEquipProprioPassagDao)
        datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
                )
            )
        )
        val result = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 2)
        assertEquals(result.getOrNull()!![1].idMovEquipProprioPassag, 2)
        assertEquals(result.getOrNull()!![1].matricColab, 19035)
    }

    @Test
    fun  `Check success add if have row is correct`() = runTest {
        val datasource = MovEquipProprioPassagRoomDatasourceImpl(movEquipProprioPassagDao)
        val result = datasource.add(19759, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultList = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(resultList.getOrNull()!!.size, 1)
        assertEquals(resultList.getOrNull()!![0].idMovEquipProprioPassag, 1)
        assertEquals(resultList.getOrNull()!![0].matricColab, 19759)
    }

    @Test
    fun `Check success delete if process execute successfully`() = runTest {
        val datasource = MovEquipProprioPassagRoomDatasourceImpl(movEquipProprioPassagDao)
        datasource.addAll(
            listOf(
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19759
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 19035
                ),
                MovEquipProprioPassagRoomModel(
                    idMovEquipProprio = 1,
                    matricColab = 1807
                )
            )
        )
        val result = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 3)
        assertEquals(result.getOrNull()!![1].idMovEquipProprioPassag, 2)
        assertEquals(result.getOrNull()!![1].matricColab, 19035)
        val resultDelete = datasource.delete(19035, 1)
        assertTrue(resultDelete.isSuccess)
        assertTrue(resultDelete.getOrNull()!!)
        val resultList = datasource.list(1)
        assertEquals(resultList.getOrNull()!!.size, 2)
        assertEquals(resultList.getOrNull()!![1].idMovEquipProprioPassag, 3)
        assertEquals(resultList.getOrNull()!![1].matricColab, 1807)

    }

}