package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.external.room.dao.variable.MovEquipProprioDao
import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.infra.models.room.variable.MovEquipProprioRoomModel
import br.com.usinasantafe.pcpcomp.utils.FlowApp
import br.com.usinasantafe.pcpcomp.utils.StatusData
import br.com.usinasantafe.pcpcomp.utils.StatusSend
import br.com.usinasantafe.pcpcomp.utils.TypeMov
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class SetNotaFiscalProprioImplTest: KoinTest {

    private val usecase: SetNotaFiscalProprio by inject()
    private val movEquipProprioSharedPreferencesDatasource: MovEquipProprioSharedPreferencesDatasource by inject()
    private val movEquipProprioDao: MovEquipProprioDao by inject()

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_nota_fiscal_is_not_number_flowapp_add() = runTest {
        val result = usecase(
            notaFiscal = "123456a",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> SetNotaFiscalProprio"
        )
        assertEquals(result.exceptionOrNull()!!.cause!!.message, "For input string: \"123456a\"")
    }

    @Test
    fun check_return_failure_if_nota_fiscal_is_not_number_flowapp_change() = runTest {
        val result = usecase(
            notaFiscal = "123456a",
            flowApp = FlowApp.CHANGE,
            id = 1
        )
        assertTrue(result.isFailure)
        assertEquals(
            result.exceptionOrNull()!!.message,
            "Failure Usecase -> SetNotaFiscalProprio"
        )
        assertEquals(result.exceptionOrNull()!!.cause!!.message, "For input string: \"123456a\"")
    }

    @Test
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal_flowapp_add() = runTest {
        val exception = try {
            usecase(
                notaFiscal = "123456",
                flowApp = FlowApp.ADD,
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
                notaFiscal = "123456",
                flowApp = FlowApp.CHANGE,
                id = 1
            )
            null
        } catch (exception: Exception){
            exception
        }
        assertEquals(exception, null)
    }

    @Test
    fun check_return_true_if_SetNotaFiscalProprio_execute_success_flowapp_add() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase(
            notaFiscal = "123456",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioSharedPreferencesDatasource.get()
        assertEquals(resultMov.getOrNull()!!.notaFiscalMovEquipProprio, 123456)
    }

    @Test
    fun check_return_true_if_SetNotaFiscalProprio_execute_success_flowapp_change() = runTest {
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
                statusSendMovEquipProprio = StatusSend.SEND
            )
        )
        val result = usecase(
            notaFiscal = "123456",
            flowApp = FlowApp.CHANGE,
            id = 1
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
        val resultMov = movEquipProprioDao.get(1)
        assertEquals(resultMov.notaFiscalMovEquipProprio, 123456)
    }
}