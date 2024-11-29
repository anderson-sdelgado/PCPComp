package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ICleanEquipSegTest: KoinTest {

    private val usecase: CleanEquipSeg  by inject()
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_clear_equip_seg_if_process_is_success() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        movEquipProprioEquipSegSharedPreferencesDatasource.add(20)
        val resultListBefore = movEquipProprioEquipSegSharedPreferencesDatasource.list()
        val passagListBefore = resultListBefore.getOrNull()!!
        assertEquals(passagListBefore.size, 2)
        val result = usecase()
        assertTrue(result.isSuccess)
        val resultListAfter = movEquipProprioEquipSegSharedPreferencesDatasource.list()
        val passagListAfter = resultListAfter.getOrNull()!!
        assertEquals(passagListAfter.size, 0)
    }
}