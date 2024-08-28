package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import br.com.usinasantafe.pcpcomp.utils.TypeOcupante
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetDestinoProprioImplTest: KoinTest {

    private val usecase: SetDestinoProprio by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal() = runTest {
        val exception = try {
            usecase(
                destino = "Teste",
                flowApp = FlowApp.ADD,
                id = 0
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_success_if_have_data_in_mov_equip_proprio_internal() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase(
            destino = "Teste",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertEquals(resultMov.getOrNull()!!.destinoMovEquipProprio, "Teste")
    }

}