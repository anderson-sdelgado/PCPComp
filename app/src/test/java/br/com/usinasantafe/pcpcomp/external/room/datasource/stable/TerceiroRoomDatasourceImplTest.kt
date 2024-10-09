package br.com.usinasantafe.pcpcomp.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcpcomp.external.room.AppDatabaseRoom
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.TerceiroDao
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.TerceiroRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TerceiroRoomDatasourceImplTest {

    private lateinit var terceiroDao: TerceiroDao
    private lateinit var db: AppDatabaseRoom

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabaseRoom::class.java).allowMainThreadQueries().build()
        terceiroDao = db.terceiroDao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `Check execution correct deleteAll`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        val result = datasource.deleteAll()
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Check failure addAll if have row repeated`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        val result = datasource.addAll(
            listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro"
                )
            )
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> TerceiroRoomDatasourceImpl.addAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: Cannot execute for last inserted row ID")
    }

    @Test
    fun `Check success addAll if have row is correct`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        val result = datasource.addAll(
            listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 2,
                    idBDTerceiro = 2,
                    cpfTerceiro = "123.456.789-99",
                    nomeTerceiro = "Terceiro 2",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
        )
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
    }

    @Test
    fun `Check return false if not exist Cpf researched`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        val result = datasource.checkCpf("123.456.789-00")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun `Check return true if exist Cpf researched`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        datasource.addAll(
            listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 2,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
        )
        val result = datasource.checkCpf("123.456.789-00")
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }

    @Test
    fun `Check return roomModel if have data researched in get id`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        datasource.addAll(
            listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 2,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
        )
        val resultList = datasource.get(1)
        assertTrue(resultList.isSuccess)
        val list = resultList.getOrNull()!!
        assertEquals(list.size, 2)
        val roomModel = list[0]
        assertEquals(roomModel.idTerceiro, 1)
        assertEquals(roomModel.empresaTerceiro, "Empresa Terceiro")
    }


    @Test
    fun `Check return roomModel if have data researched in get cpf`() = runTest {
        val datasource = TerceiroRoomDatasourceImpl(terceiroDao)
        datasource.addAll(
            listOf(
                TerceiroRoomModel(
                    idTerceiro = 1,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro"
                ),
                TerceiroRoomModel(
                    idTerceiro = 2,
                    idBDTerceiro = 1,
                    cpfTerceiro = "123.456.789-00",
                    nomeTerceiro = "Terceiro",
                    empresaTerceiro = "Empresa Terceiro 2"
                )
            )
        )
        val resultList = datasource.get("123.456.789-00")
        assertTrue(resultList.isSuccess)
        val list = resultList.getOrNull()!!
        assertEquals(list.size, 2)
        val roomModel = list[1]
        assertEquals(roomModel.idTerceiro, 2)
        assertEquals(roomModel.empresaTerceiro, "Empresa Terceiro 2")
    }
}