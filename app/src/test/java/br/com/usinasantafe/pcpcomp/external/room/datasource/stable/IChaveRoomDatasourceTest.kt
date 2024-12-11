package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ChaveDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ChaveRoomModel
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
class IChaveRoomDatasourceTest {

    private lateinit var chaveDao: ChaveDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        chaveDao = db.chaveDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check failure addAll if have row repeated`() =
        runTest {
            val qtdBefore = chaveDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = IChaveRoomDatasource(chaveDao)
            val result = datasource.addAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    ),
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    )
                )
            )
            assertTrue(result.isFailure)
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> IChaveRoomDatasource.addAll"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
            val qtdAfter = chaveDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `Check success addAll if have row is correct`() =
        runTest {
            val qtdBefore = chaveDao.listAll().size
            assertEquals(
                qtdBefore,
                0
            )
            val datasource = IChaveRoomDatasource(chaveDao)
            val result = datasource.addAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    ),
                    ChaveRoomModel(
                        idChave = 2,
                        descrChave = "02 - SERVIDOR",
                        idLocalTrab = 1
                    ),
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
            val qtdAfter = chaveDao.listAll().size
            assertEquals(
                qtdAfter,
                2
            )
        }

    @Test
    fun `Check execution correct deleteAll`() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    )
                )
            )
            val qtdBefore = chaveDao.listAll().size
            assertEquals(
                qtdBefore,
                1
            )
            val datasource = IChaveRoomDatasource(chaveDao)
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val qtdAfter = chaveDao.listAll().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `Check execution correct get`() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    ),
                    ChaveRoomModel(
                        idChave = 2,
                        descrChave = "02 - SERVIDOR",
                        idLocalTrab = 1
                    )
                )
            )
            val datasource = IChaveRoomDatasource(chaveDao)
            val result = datasource.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val entity = result.getOrNull()!!
            assertEquals(
                entity.idChave,
                1
            )
            assertEquals(
                entity.descrChave,
                "01 - TI"
            )
        }

    @Test
    fun `Check execution correct listAll`() =
        runTest {
            chaveDao.insertAll(
                listOf(
                    ChaveRoomModel(
                        idChave = 1,
                        descrChave = "01 - TI",
                        idLocalTrab = 1
                    ),
                    ChaveRoomModel(
                        idChave = 2,
                        descrChave = "02 - SERVIDOR",
                        idLocalTrab = 1
                    )
                )
            )
            val datasource = IChaveRoomDatasource(chaveDao)
            val result = datasource.listAll()
            assertEquals(
                result.isSuccess,
                true
            )
            val entityList = result.getOrNull()!!
            assertEquals(
                entityList.size,
                2
            )
            val entity = entityList[0]
            assertEquals(
                entity.idChave,
                1
            )
            assertEquals(
                entity.descrChave,
                "01 - TI"
            )
        }
}