package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IDeletePassagVisitTercTest: KoinTest {

    private val usecase: DeletePassagVisitTerc by inject()
    private val movEquipVisitTercPassagSharedPreferencesDatasource: MovEquipVisitTercPassagSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_delete_passag_if_process_is_success() = runTest {
        movEquipVisitTercPassagSharedPreferencesDatasource.add(10)
        movEquipVisitTercPassagSharedPreferencesDatasource.add(20)
        val resultListBefore = movEquipVisitTercPassagSharedPreferencesDatasource.list()
        val passagListBefore = resultListBefore.getOrNull()!!
        assertEquals(passagListBefore.size, 2)
        assertEquals(passagListBefore[0], 10)
        val result = usecase(
            idVisitTerc = 10,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        val resultListAfter = movEquipVisitTercPassagSharedPreferencesDatasource.list()
        val passagListAfter = resultListAfter.getOrNull()!!
        assertEquals(passagListAfter.size, 1)
        assertEquals(passagListAfter[0], 20)
    }
}