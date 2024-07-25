package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EquipRoomDatasourceImplTest {

    private lateinit var equipDao: EquipDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        equipDao = db.equipDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = EquipRoomDatasourceImpl(equipDao)
        val result = datasource.deleteAll()
        assertEquals(result.isSuccess, true)
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = EquipRoomDatasourceImpl(equipDao)
        val result = datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10
                ),
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10
                )
            )
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID")
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = EquipRoomDatasourceImpl(equipDao)
        val result = datasource.addAll(
            listOf(
                EquipRoomModel(
                    idEquip = 1,
                    nroEquip = 10
                ),
                EquipRoomModel(
                    idEquip = 2,
                    nroEquip = 20
                )
            )
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(true))
    }
}