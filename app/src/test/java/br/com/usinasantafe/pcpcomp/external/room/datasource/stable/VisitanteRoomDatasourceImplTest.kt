package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.VisitanteDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.VisitanteRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VisitanteRoomDatasourceImplTest {

    private lateinit var visitanteDao: VisitanteDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        visitanteDao = db.visitanteDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = VisitanteRoomDatasourceImpl(visitanteDao)
        val result = datasource.deleteAll()
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = VisitanteRoomDatasourceImpl(visitanteDao)
        val result = datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                )
            )
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> VisitanteRoomDatasourceImpl.addAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID")
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = VisitanteRoomDatasourceImpl(visitanteDao)
        val result = datasource.addAll(
            listOf(
                VisitanteRoomModel(
                    idVisitante = 1,
                    cpfVisitante = "123.456.789-00",
                    nomeVisitante = "Visitante",
                    empresaVisitante = "Empresa Visitante"
                ),
                VisitanteRoomModel(
                    idVisitante = 2,
                    cpfVisitante = "123.456.789-99",
                    nomeVisitante = "Visitante 2",
                    empresaVisitante = "Empresa Visitante 2"
                )
            )
        )
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }
}