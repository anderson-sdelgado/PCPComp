package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IStartInputMovEquipVisitTercTest : KoinTest {

    private val usecase: StartInputMovEquipVisitTerc by inject()
    private val movEquipVisitTercSharedPreferencesDatasource:
            MovEquipVisitTercSharedPreferencesDatasource by inject()
    private val movEquipVisitTercPassagSharedPreferencesDatasource:
            MovEquipVisitTercPassagSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_true_if_start_input_execute_success() =
        runTest {
            movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
            val result = usecase()
            assertTrue(result.isSuccess)
            assertTrue(result.getOrNull()!!)
            val resultGet = movEquipVisitTercSharedPreferencesDatasource.get()
            assertTrue(resultGet.isSuccess)
            val entity = resultGet.getOrNull()!!
            assertEquals(entity.tipoMovEquipVisitTerc, TypeMov.INPUT)
            val resultClear = movEquipVisitTercPassagSharedPreferencesDatasource.list()
            assertTrue(resultClear.isSuccess)
            assertTrue(resultClear.getOrNull()!!.isEmpty())
        }

}