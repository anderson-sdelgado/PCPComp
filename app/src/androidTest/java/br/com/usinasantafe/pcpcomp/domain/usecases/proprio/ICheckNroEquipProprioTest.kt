package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ICheckNroEquipProprioTest: KoinTest {

    private val usecase: CheckNroEquipProprio by inject()
    private val equipDao: EquipDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_false_if_nro_equip_is_invalid() = runTest {
        val result = usecase("100")
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!)
    }

    @Test
    fun check_return_true_if_nro_equip_is_valid() = runTest {
        val equipList = listOf(
            EquipRoomModel(
                idEquip = 10,
                nroEquip = 100
            )
        )
        equipDao.insertAll(equipList)
        val result = usecase("100")
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}