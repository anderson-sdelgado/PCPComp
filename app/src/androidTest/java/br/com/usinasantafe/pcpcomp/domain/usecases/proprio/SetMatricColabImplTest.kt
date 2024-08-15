package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.entities.variable.Config
import br.com.usinasantafe.pcpcomp.domain.usecases.config.SetMatricVigiaConfig
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetMatricColabImplTest: KoinTest {

    private val usecase: SetMatricColab by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal() = runTest {
        val exception = try {
            usecase("19759")
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_success_if_have_data_in_mov_equip_proprio_internal() = runTest {
        val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase("19759")
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertEquals(resultMov.getOrNull()!!.nroMatricColabMovEquipProprio, 19759L)
    }
}