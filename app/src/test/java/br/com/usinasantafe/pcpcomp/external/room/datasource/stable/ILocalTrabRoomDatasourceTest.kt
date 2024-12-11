package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.LocalTrabDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.LocalTrabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ILocalTrabRoomDatasourceTest {

    private lateinit var localTrabDao: LocalTrabDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        localTrabDao = db.localTrabDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check failure addAll if have row repeated`() =
        runTest {
            val qtdBefore = localTrabDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = ILocalTrabRoomDatasource(localTrabDao)
            val result = datasource.addAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    ),
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    )
                )
            )
            assertTrue(result.isFailure)
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> ILocalTrabRoomDatasource.addAll"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = localTrabDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `Check success addAll if have row is correct`() =
        runTest {
            val qtdBefore = localTrabDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = ILocalTrabRoomDatasource(localTrabDao)
            val result = datasource.addAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    ),
                    LocalTrabRoomModel(
                        idLocalTrab = 2,
                        descrLocalTrab = "CONTABILIDADE"
                    )
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result,
                Result.success(true)
            )
            val qtdAfter = localTrabDao.listAll().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `Check execution correct deleteAll`() =
        runTest {
            localTrabDao.insertAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    )
                )
            )
            val qtdBefore = localTrabDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = ILocalTrabRoomDatasource(localTrabDao)
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = localTrabDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `Check execution correct getDescr`() =
        runTest {
            localTrabDao.insertAll(
                listOf(
                    LocalTrabRoomModel(
                        idLocalTrab = 1,
                        descrLocalTrab = "TI"
                    )
                )
            )
            val datasource = ILocalTrabRoomDatasource(localTrabDao)
            val result = datasource.getDescr(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "TI"
            )
        }

}