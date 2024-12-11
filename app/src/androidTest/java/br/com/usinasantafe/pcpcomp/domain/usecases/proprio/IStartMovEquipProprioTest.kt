package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IStartMovEquipProprioTest: KoinTest {

    private val usecase: StartMovEquipProprio by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_success_if_data_is_correct() = runTest {
        val result = usecase(TypeMovEquip.INPUT)
        assertTrue(result.isSuccess)
        assertEquals(result, Result.success(true))
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertTrue(resultMov.isSuccess)
        assertEquals(resultMov.getOrNull()!!.tipoMovEquipProprio, TypeMovEquip.INPUT)
    }

}