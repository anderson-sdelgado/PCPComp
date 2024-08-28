package br.com.usinasantafe.pcpcomp.domain.usecases.proprio

import br.com.usinasantafe.pcpcomp.generateTestAppComponent
import br.com.usinasantafe.pcpcomp.infra.datasource.sharepreferences.MovEquipProprioSharedPreferencesDatasource
import br.com.usinasantafe.pcpcomp.utils.FlowApp
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

    @Before
    fun before() {
        val server = MockWebServer()
        server.start()
        loadKoinModules(generateTestAppComponent(server.url("").toString()))
    }

    @Test
    fun check_return_failure_if_nota_fiscal_is_not_number() = runTest {
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
    fun check_return_failure_if_not_have_data_in_mov_equip_proprio_internal() = runTest {
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
    fun check_return_true_if_SetNotaFiscalProprio_execute_success() = runTest {
        movEquipProprioSharedPreferencesDatasource.start(TypeMov.INPUT)
        val result = usecase(
            notaFiscal = "123456",
            flowApp = FlowApp.ADD,
            id = 0
        )
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!)
    }
}