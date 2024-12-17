package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.external.room.dao.stable.EquipDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioEquipSegDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioEquipSegSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.stable.EquipRoomModel
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeEquip
import br.com.usinasantafe.pcpcomp.utils.TypeMovEquip
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class ISetNroEquipProprioTest: KoinTest {

    private val usecase: SetNroEquipProprio by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()
    private val movEquipProprioEquipSegSharedPreferencesDatasource: MovEquipProprioEquipSegSharedPreferencesDatasource by inject()
    private val equipDAO: EquipDao by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val movEquipProprioEquipSegDao: MovEquipProprioEquipSegDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal_flowapp_add() = runTest {
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
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal_flowapp_change() = runTest {
        val exception = try {
            usecase(
                nroEquip = "100",
                flowApp = FlowApp.CHANGE,
                typeEquip = TypeEquip.VEICULO,
                id = 1
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_flowapp_add() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepositoryImpl.getId")
    }

    @Test
    fun check_return_failure_if_not_have_data_in_equip_flowapp_change() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 1
        )
        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()!!.message, "Failure Repository -> EquipRepositoryImpl.getId")
    }

    @Test
    fun check_return_true_if_have_success_in_set_id_equip_flowapp_add() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMovEquip.INPUT)
        equipDAO.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                )
            )
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULO,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertEquals(resultMov.getOrNull()!!.idEquipMovEquipProprio, 10)
    }

    @Test
    fun check_return_true_if_have_success_in_set_id_equip_flowapp_change() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENDING
            )
        )
        equipDAO.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                )
            )
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULO,
            id = 1
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioDao.get(1)
        assertEquals(resultMov.idEquipMovEquipProprio, 10)
        assertEquals(resultMov.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun check_return_true_if_add_passag_execute_correctly_in_flowapp_add() = runTest {
        equipDAO.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                )
            )
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.ADD,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultList = movEquipProprioEquipSegSharedPreferencesDatasource.list()
        assertEquals(resultList.getOrNull()!![0], 10)
    }

    @Test
    fun check_return_true_if_add_passag_execute_correctly_in_flowapp_change() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMovEquip.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 19759,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENDING
            )
        )
        equipDAO.insertAll(
            listOf(
                EquipRoomModel(
                    idEquip = 10,
                    nroEquip = 100,
                )
            )
        )
        val result = usecase(
            nroEquip = "100",
            flowApp = FlowApp.CHANGE,
            typeEquip = TypeEquip.VEICULOSEG,
            id = 1
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultList = movEquipProprioEquipSegDao.list(1)
        assertEquals(resultList[0].idEquip, 10)
        val resultMov = movEquipProprioDao.get(1)
        assertEquals(resultMov.statusSendMovEquipProprio, StatusSend.SEND)
    }
}