package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ILocalRoomDatasourceTest {

    private lateinit var localDao: LocalDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        localDao = db.localDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = ILocalRoomDatasource(localDao)
        val result = datasource.deleteAll()
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = ILocalRoomDatasource(localDao)
        val result = datasource.addAll(
            listOf(
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "USINA"
                ),
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "USINA"
                )
            )
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Datasource -> LocalRoomDatasourceImpl.addAll"
        )
        assertEquals(
            result.exceptionOrNull()!!.cause.toString(),
            "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
        )
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = ILocalRoomDatasource(localDao)
        val result = datasource.addAll(
            listOf(
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "USINA"
                ),
                LocalRoomModel(
                    idLocal = 2,
                    descrLocal = "MOTO"
                )
            )
        )
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }

    @Test
    fun `Check success getAll if have table is empty`() = runTest {
        val localRooms = listOf(
            LocalRoomModel(
                idLocal = 1,
                descrLocal = "USINA"
            ),
            LocalRoomModel(
                idLocal = 2,
                descrLocal = "MOTO"
            ),
        )
        val datasource = ILocalRoomDatasource(localDao)
        datasource.addAll(
            localRooms
        )
        val result = datasource.getAll()
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(localRooms))
    }

    @Test
    fun `Check return nomeColab if getNome is success`() = runTest {
        val datasource = ILocalRoomDatasource(localDao)
        datasource.addAll(
            listOf(
                LocalRoomModel(
                    idLocal = 1,
                    descrLocal = "USINA"
                ),
            )
        )
        val result = datasource.getDescr(1)
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!, "USINA")
    }
}