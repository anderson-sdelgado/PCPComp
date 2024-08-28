package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.domain.repositories.stable.EquipRepository
import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
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

class SetNroEquipProprioImplTest: KoinTest {

    private val usecase: SetNroEquipProprio by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()
    private val moEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()
    private val equipDAO: EquipDao by inject()

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
                nroEquip = "100",
                flowApp = FlowApp.ADD,
                typeEquip = TypeEquip.VEICULO,
                id = 0
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_failure_if_not_have_data_in_equip() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Usecase -> SetNroEquipProprio")
    }

    @Test
    fun check_return_trie_if_have_success_in_set_id_equip() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Usecase -> SetNroEquipProprio")
    }

    @Test
    fun check_return_failure_if_have_success_in_add_passag() = runTest {
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = moEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(resultMov.getOrNull()!![0], 100)
    }
}