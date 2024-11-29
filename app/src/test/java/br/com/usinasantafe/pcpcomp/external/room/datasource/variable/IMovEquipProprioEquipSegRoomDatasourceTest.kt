package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioEquipSegRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IMovEquipProprioEquipSegRoomDatasourceTest {

    private lateinit var movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        movEquipProprioEquipSegDao = db.movEquipProprioEquipSegDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = IMovEquipProprioEquipSegRoomDatasource(movEquipProprioEquipSegDao)
        val result = datasource.addAll(
            listOf(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 1
                ),
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 2
                )
            )
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check success list if have MovEquipProprioEquipSegRoomModel list`() = runTest {
        val datasource = IMovEquipProprioEquipSegRoomDatasource(movEquipProprioEquipSegDao)
        datasource.addAll(
            listOf(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 1
                ),
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 2
                )
            )
        )
        val result = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 2)
        assertEquals(result.getOrNull()!![1].idMovEquipProprioEquipSeg, 2)
        assertEquals(result.getOrNull()!![1].idEquip, 2)
    }

    @Test
    fun `Check success add if have row is correct`() = runTest {
        val datasource = IMovEquipProprioEquipSegRoomDatasource(movEquipProprioEquipSegDao)
        val result = datasource.add(10, 1)
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultList = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(resultList.getOrNull()!!.size, 1)
        assertEquals(resultList.getOrNull()!![0].idMovEquipProprioEquipSeg, 1)
        assertEquals(resultList.getOrNull()!![0].idEquip, 10)
    }

    @Test
    fun `Check success delete if process execute successfully`() = runTest {
        val datasource = IMovEquipProprioEquipSegRoomDatasource(movEquipProprioEquipSegDao)
        datasource.addAll(
            listOf(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 1
                ),
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 2
                ),
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 3
                )
            )
        )
        val result = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 3)
        assertEquals(result.getOrNull()!![1].idMovEquipProprioEquipSeg, 2)
        assertEquals(result.getOrNull()!![1].idEquip, 2)
        val resultDelete = datasource.delete(2, 1)
        assertTrue(resultDelete.isSuccess)
        assertTrue(resultDelete.getOrNull()!!)
        val resultList = datasource.list(1)
        assertEquals(resultList.getOrNull()!!.size, 2)
        assertEquals(resultList.getOrNull()!![1].idMovEquipProprioEquipSeg, 3)
        assertEquals(resultList.getOrNull()!![1].idEquip, 3)
    }


    @Test
    fun `Check success delete MovAll if process execute successfully`() = runTest {
        val datasource = IMovEquipProprioEquipSegRoomDatasource(movEquipProprioEquipSegDao)
        datasource.addAll(
            listOf(
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 1
                ),
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 2
                ),
                MovEquipProprioEquipSegRoomModel(
                    idMovEquipProprio = 1,
                    idEquip = 3
                )
            )
        )
        val result = datasource.list(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 3)
        val resultDelete = datasource.delete(1)
        assertTrue(resultDelete.isSuccess)
        assertTrue(resultDelete.getOrNull()!!)
        val resultList = datasource.list(1)
        assertEquals(resultList.getOrNull()!!.size, 0)
    }

}