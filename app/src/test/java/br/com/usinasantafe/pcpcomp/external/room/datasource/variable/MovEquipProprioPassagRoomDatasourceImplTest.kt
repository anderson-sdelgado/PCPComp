package br.com.usinasantafe.pcpcomp.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
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
}