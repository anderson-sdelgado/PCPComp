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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocalRoomDatasourceImplTest {

    private lateinit var localDao: LocalDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        localDao = db.localDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = LocalRoomDatasourceImpl(localDao)
        val result = datasource.deleteAll()
        assertEquals(result.isSuccess, true)
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = LocalRoomDatasourceImpl(localDao)
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
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID")
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = LocalRoomDatasourceImpl(localDao)
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
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(true))
    }
}