package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.ColabDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class GetPassagColabListImplTest : KoinTest {

    private val usecase: GetPassagColabList by inject()

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
    fun check_return_error_if_not_have_data_in_colab_table() = runTest {
        val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource by inject()
        movEquipProprioPassagSharedPreferencesDatasource.add(19759)
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
        val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource by inject()
        movEquipProprioPassagSharedPreferencesDatasource.add(19759)
        val colabDao: ColabDao by inject()
        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    matricColab = 19759,
                    nomeColab = "ANDERSON DA SILVA DELGADO"
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
        assertEquals(result.getOrNull()!![0].matricColab, 19759)
    }

}