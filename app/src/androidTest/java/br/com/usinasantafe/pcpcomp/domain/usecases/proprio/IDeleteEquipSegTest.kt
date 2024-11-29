package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class IDeleteEquipSegTest: KoinTest {

    private val usecase: DeleteEquipSeg by inject()
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_delete_equip_seg_if_process_is_success() = runTest {
        movEquipProprioEquipSegSharedPreferencesDatasource.add(10)
        movEquipProprioEquipSegSharedPreferencesDatasource.add(20)
        val resultListBefore = movEquipProprioEquipSegSharedPreferencesDatasource.list()
        val equipSegListBefore = resultListBefore.getOrNull()!!
        assertEquals(equipSegListBefore.size, 2)
        assertEquals(equipSegListBefore[0], 10)
        val result = usecase(
            idEquip = 10,
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        val resultListAfter = movEquipProprioEquipSegSharedPreferencesDatasource.list()
        val passagListAfter = resultListAfter.getOrNull()!!
        assertEquals(passagListAfter.size, 1)
        assertEquals(passagListAfter[0], 20)
    }
}