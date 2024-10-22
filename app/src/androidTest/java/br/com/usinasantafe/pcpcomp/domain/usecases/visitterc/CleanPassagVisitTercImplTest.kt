package br.com.usinasantafe.pcpcomp.domain.usecases.visitterc

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipVisitTercPassagSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CleanPassagVisitTercImplTest : KoinTest {

    private val usecase: CleanPassagVisitTerc by inject()
    private val movEquipVisitTercPassagSharedPreferencesDatasource: MovEquipVisitTercPassagSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clear_passag_if_process_is_success() = runTest {
        movEquipVisitTercPassagSharedPreferencesDatasource.add(19759)
        movEquipVisitTercPassagSharedPreferencesDatasource.add(19035)
        val resultListBefore = movEquipVisitTercPassagSharedPreferencesDatasource.list()
        val passagListBefore = resultListBefore.getOrNull()!!
        assertEquals(passagListBefore.size, 2)
        val result = usecase()
        assertTrue(result.isSuccess)
        val resultListAfter = movEquipVisitTercPassagSharedPreferencesDatasource.list()
        val passagListAfter = resultListAfter.getOrNull()!!
        assertEquals(passagListAfter.size, 0)
    }
}