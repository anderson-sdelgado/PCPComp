package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class CleanPassagColabImplTest: KoinTest {

    private val usecase: CleanPassagColab by inject()
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clear_passag_if_process_is_success() = runTest {
        movEquipProprioPassagSharedPreferencesDatasource.add(19759)
        movEquipProprioPassagSharedPreferencesDatasource.add(19035)
        val resultListBefore = movEquipProprioPassagSharedPreferencesDatasource.list()
        val passagListBefore = resultListBefore.getOrNull()!!
        assertEquals(passagListBefore.size, 2)
        val result = usecase()
        assertTrue(result.isSuccess)
        val resultListAfter = movEquipProprioPassagSharedPreferencesDatasource.list()
        val passagListAfter = resultListAfter.getOrNull()!!
        assertEquals(passagListAfter.size, 0)
    }
}