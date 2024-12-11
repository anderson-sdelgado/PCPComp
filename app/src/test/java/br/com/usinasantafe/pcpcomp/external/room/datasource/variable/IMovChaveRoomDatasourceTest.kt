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
    fun `listRemove - Check return failure if have error in MovChaveDao listStatusForeigner`() =
        runTest {
            val datasource = IMovChaveRoomDatasource(movChaveDao)
            val exception = try {
                datasource.listRemove()
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
            val result = datasource.listRemove()
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

}