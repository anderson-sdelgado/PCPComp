package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IGetEquipSegListTest : KoinTest {

    private val usecase: GetEquipSegList by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_error() = runTest {
        val exception = try {
            usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            null
        } catch (exception: Exception) {
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_error_if_not_have_data_in_equip_table() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        val exception = try {
            usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
            null
        } catch (exception: Exception) {
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_list_if_process_execute_successfully() = runTest {
        val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        val equipDao: EquipDao by inject()
        equipDao.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100
                )
            )
        )
        val result =
            usecase(
                flowApp = FlowApp.ADD,
                id = 0
            )
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull()!!.size, 1)
        assertEquals(result.getOrNull()!![0].nroEquip, 100)
    }

}