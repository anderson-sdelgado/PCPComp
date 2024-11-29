package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.RLocalFluxoDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.RLocalFluxoRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RLocalIFluxoRoomDatasourceTest {

    private lateinit var rLocalFluxoDao: RLocalFluxoDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        rLocalFluxoDao = db.rLocalFluxoDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `AddAll - Check return failure if have row repeated`() =
        runTest {
            val datasource = IRLocalFluxoRoomDatasource(rLocalFluxoDao)
            val result = datasource.addAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idFluxo = 1,
                        idLocal = 1
                    ),
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idFluxo = 1,
                        idLocal = 1
                    )
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "Failure Datasource -> RLocalFluxoDatasourceImpl.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID"
            )
        }

    @Test
    fun `AddAll - Check return true and check data inserted`() =
        runTest {
            val datasource = IRLocalFluxoRoomDatasource(rLocalFluxoDao)
            val result = datasource.addAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idFluxo = 1,
                        idLocal = 1
                    ),
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 2,
                        idFluxo = 2,
                        idLocal = 1
                    )
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = rLocalFluxoDao.listAll()
            assertEquals(
                list.size,
                2
            )
            val roomModel = list[1]
            assertEquals(
                roomModel.idFluxo,
                2
            )
            assertEquals(
                roomModel.idFluxo,
                2
            )
        }

    @Test
    fun `DeleteAll - Check return true and check data inserted`() =
        runTest {
            val datasource = IRLocalFluxoRoomDatasource(rLocalFluxoDao)
            val resultAdd = datasource.addAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idFluxo = 1,
                        idLocal = 1
                    ),
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 2,
                        idFluxo = 2,
                        idLocal = 1
                    )
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            val listBefore = rLocalFluxoDao.listAll()
            assertEquals(
                listBefore.size,
                2
            )
            val resultDelete = datasource.deleteAll()
            assertEquals(
                resultDelete.isSuccess,
                true
            )
            val listAfter = rLocalFluxoDao.listAll()
            assertEquals(
                listAfter.size,
                0
            )
        }

    @Test
    fun `List - Check return true and check data inserted`() =
        runTest {
            val datasource = IRLocalFluxoRoomDatasource(rLocalFluxoDao)
            val resultAdd = datasource.addAll(
                listOf(
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 1,
                        idFluxo = 1,
                        idLocal = 1
                    ),
                    RLocalFluxoRoomModel(
                        idRLocalFluxo = 2,
                        idFluxo = 2,
                        idLocal = 1
                    )
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            val result = datasource.list(1)
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val roomModel = list[1]
            assertEquals(
                roomModel.idFluxo,
                2
            )
            assertEquals(
                roomModel.idLocal,
                1
            )
        }
}