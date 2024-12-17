package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovChaveDao
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovChaveRoomModel
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusForeigner
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMovKey
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class IMovChaveRoomDatasourceTest {

    private lateinit var movChaveDao: MovChaveDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        movChaveDao = db.movChaveDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `listInside - Check return failure if have error in MovChaveDao listStatusForeigner`() =
        runTest {
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val exception = try {
                datasource.listInside()
                null
            } catch (exception: Exception) {
                exception
            }
            assertEquals(
                exception,
                null
            )
        }

    @Test
    fun `listRemove - Check return correct if function execute successfully`() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 2,
                    idChaveMovChave = 2,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.OUTSIDE
                )
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.listInside()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val modelRoom = list[0]
            assertEquals(
                modelRoom.idLocalMovChave,
                1
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            val modelList = movChaveDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                modelList.size,
                0
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.save(roomModel)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1L
            )
            val modelListAfter = movChaveDao.listStatusForeigner(StatusForeigner.INSIDE)
            assertEquals(
                modelListAfter.size,
                1
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.get(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model,
                roomModel
            )
            assertEquals(
                model.idMovChave,
                1
            )
            assertEquals(
                model.idLocalMovChave,
                1
            )
        }

    @Test
    fun `setOutside - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val listOpenBefore = movChaveDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listOpenBefore.size,
                1
            )
            assertEquals(
                listOpenBefore[0].idMovChave,
                1
            )
            assertEquals(
                listOpenBefore[0].statusForeignerMovChave,
                StatusForeigner.INSIDE
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.setOutside(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val model = movChaveDao.get(1)
            assertEquals(
                model.idMovChave,
                1
            )
            assertEquals(
                model.statusForeignerMovChave,
                StatusForeigner.OUTSIDE
            )
            val listOpenAfter = movChaveDao.listStatusData(StatusData.OPEN)
            assertEquals(
                listOpenAfter.size,
                1
            )
            assertEquals(
                listOpenAfter[0].idMovChave,
                1
            )
            assertEquals(
                listOpenAfter[0].statusForeignerMovChave,
                StatusForeigner.OUTSIDE
            )
        }

    @Test
    fun `listOpen - Check return correct if function execute successfully`() =
        runTest {
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 1,
                    idChaveMovChave = 1,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.OPEN,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.INSIDE
                )
            )
            movChaveDao.insert(
                MovChaveRoomModel(
                    dthrMovChave = Date().time,
                    tipoMovChave = TypeMovKey.REMOVE,
                    matricVigiaMovChave = 19759,
                    idLocalMovChave = 2,
                    idChaveMovChave = 2,
                    matricColabMovChave = 19035,
                    observMovChave = "OBSERV",
                    statusMovChave = StatusData.CLOSE,
                    statusSendMovChave = StatusSend.SEND,
                    statusForeignerMovChave = StatusForeigner.OUTSIDE
                )
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.listOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val modelRoom = list[0]
            assertEquals(
                modelRoom.idLocalMovChave,
                1
            )
        }

    @Test
    fun `setClose - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val modelBefore = movChaveDao.get(1)
            assertEquals(
                modelBefore.idMovChave,
                1
            )
            assertEquals(
                modelBefore.statusForeignerMovChave,
                StatusForeigner.INSIDE
            )
            assertEquals(
                modelBefore.statusMovChave,
                StatusData.OPEN
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.setClose(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveDao.get(1)
            assertEquals(
                modelAfter.idMovChave,
                1
            )
            assertEquals(
                modelAfter.statusForeignerMovChave,
                StatusForeigner.INSIDE
            )
            assertEquals(
                modelAfter.statusMovChave,
                StatusData.CLOSE
            )
        }

    @Test
    fun `setIdChave - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val modelBefore = movChaveDao.get(1)
            assertEquals(
                modelBefore.idChaveMovChave,
                1
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.setIdChave(2, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveDao.get(1)
            assertEquals(
                modelAfter.idChaveMovChave,
                2
            )
        }

    @Test
    fun `setMatricColab - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val modelBefore = movChaveDao.get(1)
            assertEquals(
                modelBefore.matricColabMovChave,
                19035
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.setMatricColab(18017, 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveDao.get(1)
            assertEquals(
                modelAfter.matricColabMovChave,
                18017
            )
        }

    @Test
    fun `setObserv - Check return correct if function execute successfully`() =
        runTest {
            val roomModel = MovChaveRoomModel(
                idMovChave = 1,
                dthrMovChave = Date().time,
                tipoMovChave = TypeMovKey.REMOVE,
                matricVigiaMovChave = 19759,
                idLocalMovChave = 1,
                idChaveMovChave = 1,
                matricColabMovChave = 19035,
                observMovChave = "OBSERV",
                statusMovChave = StatusData.OPEN,
                statusSendMovChave = StatusSend.SEND,
                statusForeignerMovChave = StatusForeigner.INSIDE
            )
            movChaveDao.insert(roomModel)
            val modelBefore = movChaveDao.get(1)
            assertEquals(
                modelBefore.observMovChave,
                "OBSERV"
            )
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val result = datasource.setObserv("OBSERV ALTERADO", 1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelAfter = movChaveDao.get(1)
            assertEquals(
                modelAfter.observMovChave,
                "OBSERV ALTERADO"
            )
        }
}