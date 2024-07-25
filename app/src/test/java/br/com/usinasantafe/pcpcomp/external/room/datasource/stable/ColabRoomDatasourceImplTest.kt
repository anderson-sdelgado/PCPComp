package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ColabRoomDatasourceImplTest {

    private lateinit var colabDao: ColabDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        colabDao = db.colabDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = ColabRoomDatasourceImpl(colabDao)
        val result = datasource.deleteAll()
        assertEquals(result.isSuccess, true)
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = ColabRoomDatasourceImpl(colabDao)
        val result = datasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                )
            )
        )
        assertEquals(result.isFailure, true)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID")
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = ColabRoomDatasourceImpl(colabDao)
        val result = datasource.addAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
                ),
                ColabRoomModel(
                    matricColab = 19035,
                    nomeColab = "JOSE DONIZETE ALVES DA SILVA"
                )
            )
        )
        assertEquals(result.isSuccess, true)
        assertEquals(result, Result.success(true))
    }
}