package br.com.usinasantafe.pcpcomp.domain.usecases.updatetable.savetable

import br.com.usinasantafe.pcpcomp.domain.entities.stable.Equip
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SaveAllEquipImplTest: KoinTest {

    private val usecase: SaveEquip by inject()
    private val equipDao: EquipDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 1,
                nroEquip = 10,
                descrEquip = "teste"
            )
        )
        val result = usecase(equipList)
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
        val listBefore = equipDao.listAll()
        assertEquals(
            listBefore.size,
            1
        )
    }

    @Test
    fun check_return_failure_if_have_row_repeated() = runTest {
        val equipList = listOf(
            Equip(
                idEquip = 1,
                nroEquip = 10,
                descrEquip = "teste"
            ),
            Equip(
                idEquip = 1,
                nroEquip = 10,
                descrEquip = "teste"
            )
        )
        val result = usecase(equipList)
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Datasource -> EquipRoomDatasourceImpl.addAll")
        assertEquals(result.exceptionOrNull()!!.cause.toString(), "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_equip.idEquip (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")
    }

}