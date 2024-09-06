package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioPassagDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioPassagSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
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

class SetMatricColabImplTest: KoinTest {

    private val usecase: SetMatricColab by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()
    private val movEquipProprioPassagSharedPreferencesDatasource: MovEquipProprioPassagSharedPreferencesDatasource by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()
    private val movEquipProprioPassagDao: MovEquipProprioPassagDao by inject()

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
                matricColab = "19759",
                flowApp = FlowApp.ADD,
                typeOcupante = TypeOcupante.MOTORISTA,
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
                matricColab = "19759",
                flowApp = FlowApp.CHANGE,
                typeOcupante = TypeOcupante.MOTORISTA,
                id = 1
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_success_if_have_data_in_mov_equip_proprio_internal_flowapp_add() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.MOTORISTA,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertEquals(resultMov.getOrNull()!!.matricColabMovEquipProprio, 19759)
    }

    @Test
    fun check_return_success_if_have_data_in_mov_equip_proprio_internal_flowapp_change() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 18017,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENDING
            )
        )
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.CHANGE,
            typeOcupante = TypeOcupante.MOTORISTA,
            id = 1
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioDao.get(1)
        assertEquals(resultMov.matricColabMovEquipProprio, 19759)
        assertEquals(resultMov.statusSendMovEquipProprio, StatusSend.SEND)
    }

    @Test
    fun check_return_true_if_have_success_in_add_passag_flowapp_add() = runTest {
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.ADD,
            typeOcupante = TypeOcupante.PASSAGEIRO,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultList = movEquipProprioPassagSharedPreferencesDatasource.list()
        assertEquals(resultList.getOrNull()!![0], 19759)
    }

    @Test
    fun check_return_true_if_have_success_in_add_passag_flowapp_change() = runTest {
        movEquipProprioDao.insert(
            MovEquipProprioRoomModel(
                idMovEquipProprio = 1,
                matricVigiaMovEquipProprio = 19759,
                idLocalMovEquipProprio = 1,
                tipoMovEquipProprio = TypeMov.INPUT,
                dthrMovEquipProprio = 1723213270250,
                idEquipMovEquipProprio = 1,
                matricColabMovEquipProprio = 18017,
                destinoMovEquipProprio = "TESTE DESTINO",
                notaFiscalMovEquipProprio = 123456789,
                observMovEquipProprio = "TESTE OBSERV",
                statusMovEquipProprio = StatusData.OPEN,
                statusSendMovEquipProprio = StatusSend.SENDING
            )
        )
        val result = usecase(
            matricColab = "19759",
            flowApp = FlowApp.CHANGE,
            typeOcupante = TypeOcupante.PASSAGEIRO,
            id = 1
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultList = movEquipProprioPassagDao.list(1)
        assertEquals(resultList[0].matricColab, 19759)
        val resultMov = movEquipProprioDao.get(1)
        assertEquals(resultMov.matricColabMovEquipProprio, 18017)
        assertEquals(resultMov.statusSendMovEquipProprio, StatusSend.SEND)
    }
}